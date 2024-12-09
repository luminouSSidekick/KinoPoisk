package com.example.films.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.films.data.ListItem

class ListItemsDiff : DiffUtil.ItemCallback<ListItem>() {

    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        val isSameType = oldItem.type == newItem.type
        val isSameId = oldItem.id == newItem.id

        return isSameType && isSameId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.data == newItem.data && oldItem.settings == newItem.settings
    }

    override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Any {
        return true
    }
}