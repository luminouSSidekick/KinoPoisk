package com.example.films.ui.adapters.films

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.films.data.ListItem
import com.example.films.data.ListItemType
import com.example.films.ui.ListItemsDiff
import com.example.films.ui.adapters.BaseAdapter
import com.example.films.ui.views.BaseVH
import com.example.films.ui.views.films.FilmVH
import com.example.films.ui.views.films.FilmVHListener
import com.example.films.ui.views.films.GenreVH
import com.example.films.ui.views.films.GenreVHListener
import com.example.films.ui.views.films.HeaderVH

class FilmsRecyclerAdapter(
    layoutInflater: LayoutInflater,
    private val genreVHListener: GenreVHListener,
    private val filmVHListener: FilmVHListener
) : BaseAdapter<ListItem, BaseVH>(layoutInflater, ListItemsDiff()) {

    override fun onBindViewHolder(
        holder: BaseVH,
        item: ListItem
    ) {
        when (item.type) {
            ListItemType.HEADER_ITEM -> (holder as HeaderVH).bind(item)
            ListItemType.LIST_ITEM -> (holder as GenreVH).bind(item, genreVHListener)
            ListItemType.CARD_FILM_ITEM -> (holder as FilmVH).bind(item, filmVHListener)

            else -> throwException(
                "Unknown type on main adapter"
            )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseVH {

        return when (getItemType(viewType)) {
            ListItemType.HEADER_ITEM -> { HeaderVH(layoutInflater, parent) }
            ListItemType.LIST_ITEM -> { GenreVH(layoutInflater, parent) }
            ListItemType.CARD_FILM_ITEM -> { FilmVH(layoutInflater, parent) }

            else -> throwException(
                "Unknown type on main adapter"
            )
        }
    }

    override fun getItemViewType(
        position: Int
    ): Int {

        return when (getItem(position).type) {
            ListItemType.HEADER_ITEM -> 0
            ListItemType.LIST_ITEM -> 1
            ListItemType.CARD_FILM_ITEM -> 2

            else -> throwException(
                "Unknown type on main adapter"
            )
        }
    }

    fun getItemType(
        value: Int
    ): ListItemType {
        return when (value) {
            0 -> ListItemType.HEADER_ITEM
            1 -> ListItemType.LIST_ITEM
            2 -> ListItemType.CARD_FILM_ITEM

            else -> throwException(
                "Unknown type on main adapter"
            )
        }
    }
}