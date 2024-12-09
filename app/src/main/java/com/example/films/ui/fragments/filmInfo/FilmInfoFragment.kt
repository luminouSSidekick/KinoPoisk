package com.example.films.ui.fragments.filmInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.films.ListExtension
import com.example.films.data.FilmCharacteristicData
import com.example.films.data.ListItem
import com.example.films.data.ListItemType
import com.example.films.databinding.FilmInfoFragmentBinding
import com.example.films.entities.FilmInfo
import com.example.films.extensions.repeatOnViewLifecycleStart
import com.example.films.ui.adapters.filmInfo.FIlmInfoRecyclerAdapter
import com.example.films.viewModel.filmInfo.FilmDetailViewModel
import com.example.films.viewModel.filmInfo.FilmInfoScreenState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FilmInfoFragment : Fragment() {

    private val arguments by navArgs<FilmInfoFragmentArgs>()
    private val viewModel by viewModel<FilmDetailViewModel> {
        parametersOf(arguments.film)
    }

    private val binding get() = _binding!!
    private var _binding: FilmInfoFragmentBinding? = null

    private var adapter: FIlmInfoRecyclerAdapter? = null
    private var listExtension: ListExtension? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilmInfoFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        initViews()

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        repeatOnViewLifecycleStart {
            launch { observeState() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        initList()
    }

    private fun initList() {
        adapter = FIlmInfoRecyclerAdapter(layoutInflater)
        listExtension = ListExtension(binding.list)
        listExtension?.setAdapter(adapter!!)
    }

    private suspend fun observeState() {
        viewModel.stateFlow.collect({ state ->
            when (state) {
                is FilmInfoScreenState.Initial -> {}
                is FilmInfoScreenState.Success -> {
                    createFilmInfoItems(state.filmInfo)
                    setupTitle(state.filmInfo.name)
                }
            }
        })
    }

    private fun setupTitle(title: String?) {
        activity?.title = title
    }


    private fun createFilmInfoItems(
        filmInfo: FilmInfo
    ) {
        val items: MutableList<ListItem> = arrayListOf()
        items.add(
            ListItem(
                type = ListItemType.IMG_ITEM,
                data = filmInfo.image_url
            )
        )
        items.add(
            ListItem(
                type = ListItemType.CHARACTERISTIC_ITEM,
                data = FilmCharacteristicData(
                    name = filmInfo.localized_name,
                    genre = filmInfo.genres?.joinToString(", "),
                    year = filmInfo.year.toString(),
                    rating = filmInfo.rating
                )
            )
        )
        items.add(
            ListItem(
                type = ListItemType.DESCRIPTION_ITEM,
                data = filmInfo.description
            )
        )
        adapter?.updateWithDiffUtils(items)
    }
}