package com.example.films

import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListExtension(private var list: RecyclerView?) {

    init {
        setLayoutManager()
        addOnItemTouchListener()
    }

    fun <VH : RecyclerView.ViewHolder> setAdapter(adapter: RecyclerView.Adapter<VH>) {
        list?.adapter = adapter
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager = getDefaultLayoutManager()) {
        list?.layoutManager = layoutManager
    }

    private fun getDefaultLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(
            null,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun addOnItemTouchListener() {
        list?.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return false
            }
        })
    }
}