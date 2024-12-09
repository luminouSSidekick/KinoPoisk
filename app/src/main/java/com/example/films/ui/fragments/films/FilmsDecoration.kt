package com.example.films.ui.fragments.films

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FilmsDecoration(
    context: Context,
    space: Int = 12
) : RecyclerView.ItemDecoration() {

    private var offsetToFilms: Int = 0

    private val spaceInDp = dpToPx(
        context,
        space
    )

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position < offsetToFilms) return

        if (position % 2 == 1) {
            outRect.left = spaceInDp
            return
        }

        outRect.right = spaceInDp
    }

    fun setOffsetToFilms(
        offset: Int
    ){
        offsetToFilms = offset
    }

    private fun dpToPx(
        context: Context,
        dp: Int
    ): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

}