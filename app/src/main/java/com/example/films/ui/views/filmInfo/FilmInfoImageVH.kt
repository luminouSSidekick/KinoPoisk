package com.example.films.ui.views.filmInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.films.R
import com.example.films.data.ListItem
import com.example.films.databinding.FilmInfoImageVhBinding
import com.example.films.ui.views.BaseVH
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class FilmInfoImageVH(
    layoutInflater: LayoutInflater,
    parent: ViewGroup
) : BaseVH(layoutInflater, parent, R.layout.film_info_image_vh) {

    private val binding = FilmInfoImageVhBinding.bind(itemView)

    private var imageString: String? = null

    fun bind(listItem: ListItem) {
        this.imageString = listItem.data as? String
        showImage()
    }

    private fun showImage() {
        if (imageString == null) {
            binding.filmInfoImg.setImageResource(R.mipmap.img_empty_film_layer)
            return
        }

        Picasso.get().load(imageString).into(binding.filmInfoImg, object : Callback {
            override fun onSuccess() {}

            override fun onError(e: Exception?) {
                binding.filmInfoImg.setImageResource(R.mipmap.img_empty_film_layer)
            }
        })
    }
}