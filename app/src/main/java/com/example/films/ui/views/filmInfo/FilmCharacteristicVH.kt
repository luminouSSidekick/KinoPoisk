package com.example.films.ui.views.filmInfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.films.R
import com.example.films.data.FilmCharacteristicData
import com.example.films.data.ListItem
import com.example.films.databinding.FilmCharacteristicVhBinding
import com.example.films.ui.views.BaseVH
import java.util.Locale


class FilmCharacteristicVH(
    layoutInflater: LayoutInflater,
    parent: ViewGroup
) : BaseVH(layoutInflater, parent, R.layout.film_characteristic_vh) {

    private val binding = FilmCharacteristicVhBinding.bind(itemView)

    private lateinit var characteristicData: FilmCharacteristicData

    fun bind(listItem: ListItem) {
        this.characteristicData = listItem.data as FilmCharacteristicData
        showName()
        showGenre()
        showRating()
    }

    private fun showRating() {
        if (characteristicData.rating == null) {
            binding.sourceTextView.visibility = View.GONE
            binding.ratingTextView.visibility = View.GONE
            return
        }

        binding.ratingTextView.text = "%.1f".format(Locale.ROOT, characteristicData.rating)
    }

    private fun showName() {
        binding.filmName.text = characteristicData.name
    }

    private fun showGenre() {
        var genreText = ""

        if (characteristicData.genre?.isEmpty() == false) {
            genreText += "${(characteristicData.genre)}"

            if (characteristicData.year?.isEmpty() == false) {
                genreText += ", ${(characteristicData.year ?: "")} год"
            }

            binding.genreTextView.text = genreText
            return
        }

        if (characteristicData.year?.isEmpty() == false) {
            binding.genreTextView.text = "${characteristicData.year} год"
            return
        }

        binding.genreTextView.visibility = View.GONE
    }
}