package com.example.films.ui.fragments.films

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.example.films.data.ListItemType
import com.example.films.ui.adapters.films.FilmsRecyclerAdapter

class FilmsLayoutManager(
    context: Context,
    spanSize: Int,
    adapter: FilmsRecyclerAdapter
) : GridLayoutManager(context, spanSize) {

    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val type = adapter.getItemViewType(position)

                return when (adapter.getItemType(type)) {
                    ListItemType.HEADER_ITEM -> 2
                    ListItemType.LIST_ITEM -> 2
                    ListItemType.CARD_FILM_ITEM -> 1
                    else -> throwException()
                }
            }
        }
    }

    private fun throwException(): Nothing {
        throw IllegalStateException("Error type film manager")
    }
}