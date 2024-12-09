package com.example.films.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.films.ui.adapters.films.DiffUtilsUpdater
import com.example.films.ui.views.BaseVH

abstract class BaseAdapter<ItemClass, VH : BaseVH>(
    val layoutInflater: LayoutInflater,
    diffUtil: DiffUtil.ItemCallback<ItemClass>
) : RecyclerView.Adapter<VH>(), DiffUtilsUpdater<ItemClass> {


    private val differ = AsyncListDiffer(this, diffUtil)

    protected abstract fun onBindViewHolder(
        holder: VH,
        item: ItemClass
    )

    abstract override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(
        holder: VH,
        position: Int,
        payloads: List<Any>
    ) {
        val item = getItem(position)
        onBindViewHolder(holder, item, payloads)
    }

    override fun updateWithDiffUtils(
        items: List<ItemClass>
    ) {
        submitList(items)
    }

    protected open fun onBindViewHolder(
        holder: VH,
        item: ItemClass,
        payloads: List<Any>
    ) {
        onBindViewHolder(holder, item)
    }

    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) {
        val item = getItem(position)
        onBindViewHolder(holder, item)
    }

    protected fun getItem(
        position: Int
    ): ItemClass = differ.currentList[position]

    protected fun getItems(): List<ItemClass> = differ.currentList

    protected fun submitList(
        items: List<ItemClass>
    ) {
        differ.submitList(items)
    }

    protected fun throwException(
        messageException: String
    ): Nothing {
        throw IllegalStateException(messageException)
    }
}