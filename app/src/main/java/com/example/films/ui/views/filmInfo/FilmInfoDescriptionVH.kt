package com.example.films.ui.views.filmInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.films.R
import com.example.films.data.ListItem
import com.example.films.databinding.FilmInfoDescriptionVhBinding
import com.example.films.ui.views.BaseVH


class FilmInfoDescriptionVH(
    layoutInflater: LayoutInflater,
    parent: ViewGroup
) : BaseVH(layoutInflater, parent, R.layout.film_info_description_vh) {

    private val binding = FilmInfoDescriptionVhBinding.bind(itemView)

    private var descriptionString: String? = null

    fun bind(listItem: ListItem) {
        this.descriptionString = listItem.data as? String
        showDescription()
    }

    private fun showDescription() {
        if (descriptionString?.isNotEmpty() == true)
            binding.descriptionTextView.text = descriptionString
    }
}