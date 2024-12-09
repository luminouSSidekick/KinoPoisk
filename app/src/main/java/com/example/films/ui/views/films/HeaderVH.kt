package com.example.films.ui.views.films

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.films.R
import com.example.films.data.ListItem
import com.example.films.data.CategoryHeader
import com.example.films.databinding.HeaderVhBinding
import com.example.films.ui.views.BaseVH

class HeaderVH(
    layoutInflater: LayoutInflater,
    parent: ViewGroup
) : BaseVH(layoutInflater, parent, R.layout.header_vh) {

    private val binding = HeaderVhBinding.bind(itemView)

    private lateinit var headerData: CategoryHeader

    fun bind(listItem: ListItem) {
        this.headerData = listItem.data as CategoryHeader
        showHeader()
    }

    private fun showHeader() {
        binding.textViewHeaderVh.text = headerData.header
    }

}