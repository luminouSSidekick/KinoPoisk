package com.example.films.ui.fragments.films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.films.ListExtension
import com.example.films.data.FilmData
import com.example.films.data.ListItem
import com.example.films.data.ListItemType
import com.example.films.data.CategoryHeader
import com.example.films.entities.FilmInfo
import com.example.films.data.GenreData
import com.example.films.databinding.FilmsFragmentBinding
import com.example.films.extensions.repeatOnViewLifecycleStart
import com.example.films.ui.adapters.films.FilmsRecyclerAdapter
import com.example.films.ui.views.films.FilmVHListener
import com.example.films.ui.views.films.GenreVHListener
import com.example.films.viewModel.films.FilmsViewModel
import com.example.films.viewModel.NavigationActionViewModel
import com.example.films.viewModel.films.FilmStateEvent
import com.example.films.viewModel.films.FilmsScreenState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmsFragment : Fragment(), FilmVHListener, GenreVHListener {

    private var _binding: FilmsFragmentBinding? = null
    private val binding get() = _binding!!

    private val navigationActionViewModel by activityViewModels<NavigationActionViewModel>()
    private val viewModal by viewModel<FilmsViewModel>()

    private var adapter: FilmsRecyclerAdapter? = null
    private var listExtension: ListExtension? = null
    private var filmDecoration: FilmsDecoration? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilmsFragmentBinding.inflate(
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
            launch { observeEvent() }
        }

        binding.errorView.repeatBtn.setOnClickListener {
            viewModal.onErrorRepeatButtonClick()
        }
    }

    override fun onFilmCellClick(
        id: Int
    ) {
        viewModal.onFilmClicked(id)
    }

    override fun onGenreVHClick(
        id: String
    ) {
        viewModal.onGenreClicked(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun observeState() {
        viewModal.stateFlow.collect({ state ->
            when (state) {
                is FilmsScreenState.Loading -> {
                    binding.errorView.errorView.visibility = View.INVISIBLE
                    binding.progressBarLoading.show()
                }

                is FilmsScreenState.Success -> {
                    createFilmItems(state.filmData)
                }

                is FilmsScreenState.Error -> {
                    binding.progressBarLoading.hide()
                    binding.errorView.errorView.visibility = View.VISIBLE
                }
            }
        })
    }

    private suspend fun observeEvent() {
        viewModal.screenEventFlow.collect { event ->
            when (event) {
                is FilmStateEvent.ShowFilmInfo -> handleShowFilmInfoEvent(event)
            }
        }
    }

    private fun initViews() {
        initList()
        setupTitle("Фильмы")
    }

    private fun setupTitle(
        title: String
    ) {
        activity?.title = title
    }

    private fun initList() {
        adapter = FilmsRecyclerAdapter(
            layoutInflater,
            filmVHListener = this,
            genreVHListener = this
        )

        listExtension = ListExtension(binding.list)
        listExtension?.setAdapter(adapter!!)

        context?.let { FilmsLayoutManager(it, 2, adapter!!) }?.let {
            listExtension?.setLayoutManager(
                it
            )
        }
        context?.let {
            filmDecoration = FilmsDecoration(it)
        }
        filmDecoration?.let {
            binding.list.addItemDecoration(it)
        }
    }

    private fun createFilmItems(
        filmData: FilmData
    ) {
        val items: MutableList<ListItem> = arrayListOf()
        items.add(
            ListItem(
                type = ListItemType.HEADER_ITEM,
                data = CategoryHeader(
                    0,
                    "Жанры"
                )
            )
        )
        items.addAll(
            createGenreListItems(
                selectedGenreId = filmData.selectGenreId,
                filmsInfo = filmData.genreList
            )
        )
        items.add(
            ListItem(
                type = ListItemType.HEADER_ITEM,
                data = CategoryHeader(
                    1,
                    "Фильмы"
                )
            )
        )

        filmDecoration?.setOffsetToFilms(items.size)
        items.addAll(createFilmListItems(filmData.filmInfoList))

        if (items.isNotEmpty()) binding.progressBarLoading.hide()

        adapter?.updateWithDiffUtils(items)
    }

    private fun createFilmListItems(
        filmsInfo: List<FilmInfo>
    ): List<ListItem> {
        return filmsInfo.map {
            ListItem(
                type = ListItemType.CARD_FILM_ITEM,
                data = it
            )
        }
    }

    private fun createGenreListItems(
        filmsInfo: List<GenreData>,
        selectedGenreId: String?
    ): List<ListItem> {

        return filmsInfo.map {
            ListItem(
                type = ListItemType.LIST_ITEM,
                data = GenreData(
                    isSelected = it.id == selectedGenreId,
                    id = it.id,
                    text = it.text
                )
            )
        }
    }

    private fun handleShowFilmInfoEvent(
        event: FilmStateEvent.ShowFilmInfo
    ) {
        navigationActionViewModel.navigationToFilmInfoCreation(event.filmInfo)
    }
}