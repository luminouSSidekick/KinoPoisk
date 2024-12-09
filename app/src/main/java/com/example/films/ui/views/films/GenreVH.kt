package com.example.films.ui.views.films

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.films.R
import com.example.films.data.ListItem
import com.example.films.databinding.GenreVhBinding
import com.example.films.data.GenreData
import com.example.films.ui.views.BaseVH

interface GenreVHListener {
    fun onGenreVHClick(id: String)
}

class GenreVH(
    layoutInflater: LayoutInflater,
    parent: ViewGroup
) : BaseVH(layoutInflater, parent, R.layout.genre_vh) {

    private val binding = GenreVhBinding.bind(itemView)

    private lateinit var genreData: GenreData
    private lateinit var listener: GenreVHListener

    fun bind(listItem: ListItem, listener: GenreVHListener) {
        this.genreData = listItem.data as GenreData
        this.listener = listener
        showHeader()
        setupListeners()
    }

    private fun setupListeners() {
        binding.root.setOnClickListener {
            listener.onGenreVHClick(genreData.id)
        }
    }

    private fun showHeader() {
        binding.root.background =
            if (genreData.isSelected) ContextCompat.getDrawable(context, R.color.select_item)
            else ContextCompat.getDrawable(context, R.color.unselect_item)

        binding.textView.text = genreData.text
    }
}