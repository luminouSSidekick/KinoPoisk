package com.example.films.ui.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseVH(view: View) : RecyclerView.ViewHolder(view) {

    protected val context
        get() = itemView.context!!

    constructor(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        @LayoutRes contentLayoutId: Int,
    ) : this(layoutInflater.inflate(contentLayoutId, parent, false))
}
