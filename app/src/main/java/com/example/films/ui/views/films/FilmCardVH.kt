package com.example.films.ui.views.films

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.films.R
import com.example.films.data.ListItem
import com.example.films.databinding.FilmCardVhBinding
import com.example.films.entities.FilmInfo
import com.example.films.ui.views.BaseVH
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


interface FilmVHListener {
    fun onFilmCellClick(id: Int)
}

class FilmVH(
    layoutInflater: LayoutInflater,
    parent: ViewGroup
) : BaseVH(layoutInflater, parent, R.layout.film_card_vh) {

    private val binding = FilmCardVhBinding.bind(itemView)

    private lateinit var filmInfo: FilmInfo
    private lateinit var listener: FilmVHListener

    fun bind(
        listItem: ListItem,
        listener: FilmVHListener
    ) {
        binding.imageView.setImageResource(R.mipmap.img_empty_film)
        this.filmInfo = listItem.data as FilmInfo
        this.listener = listener

        showImage()
        showFilmTitle()
        setupListeners()
    }

    private fun setupListeners() {
        binding.root.setOnClickListener {
            listener.onFilmCellClick(filmInfo.id)
        }
    }

    private fun showImage(){
        Picasso.get().load(filmInfo.image_url).into(binding.imageView, object : Callback {
            override fun onSuccess() {}

            override fun onError(e: Exception?) {
                binding.imageView.setImageResource(R.mipmap.img_empty_film_layer)
            }
        })
    }

    private fun showFilmTitle() {
        binding.textView.text = filmInfo.localized_name
    }
}