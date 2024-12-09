package com.example.films.ui.adapters.filmInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.films.data.ListItem
import com.example.films.data.ListItemType
import com.example.films.ui.ListItemsDiff
import com.example.films.ui.adapters.BaseAdapter
import com.example.films.ui.views.BaseVH
import com.example.films.ui.views.filmInfo.FilmCharacteristicVH
import com.example.films.ui.views.filmInfo.FilmInfoDescriptionVH
import com.example.films.ui.views.filmInfo.FilmInfoImageVH

class FIlmInfoRecyclerAdapter(
    layoutInflater: LayoutInflater,
) : BaseAdapter<ListItem, BaseVH>(layoutInflater, ListItemsDiff()) {

    override fun onBindViewHolder(
        holder: BaseVH,
        item: ListItem
    ) {
        when (item.type) {
            ListItemType.IMG_ITEM -> (holder as FilmInfoImageVH).bind(item)
            ListItemType.CHARACTERISTIC_ITEM -> (holder as FilmCharacteristicVH).bind(item)
            ListItemType.DESCRIPTION_ITEM -> (holder as FilmInfoDescriptionVH).bind(item)

            else -> throwException(
                "Unknown type on filmInfo adapter"
            )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseVH {
        return when (getItemType(viewType)) {
            ListItemType.IMG_ITEM -> { FilmInfoImageVH(layoutInflater, parent) }
            ListItemType.CHARACTERISTIC_ITEM -> { FilmCharacteristicVH(layoutInflater, parent) }
            ListItemType.DESCRIPTION_ITEM -> { FilmInfoDescriptionVH(layoutInflater, parent) }

            else -> throwException(
                "Unknown type on filmInfo adapter"
            )
        }
    }

    override fun getItemViewType(
        position: Int
    ): Int {
        return when (getItem(position).type) {
            ListItemType.IMG_ITEM -> 0
            ListItemType.CHARACTERISTIC_ITEM -> 1
            ListItemType.DESCRIPTION_ITEM -> 2
            else -> throwException(
                "Unknown type on filmInfo adapter"
            )
        }
    }

    private fun getItemType(
        value: Int
    ): ListItemType {
        return when (value) {
            0 -> ListItemType.IMG_ITEM
            1 -> ListItemType.CHARACTERISTIC_ITEM
            2 -> ListItemType.DESCRIPTION_ITEM

            else -> throwException(
                "Unknown type on filmInfo adapter"
            )
        }
    }
}