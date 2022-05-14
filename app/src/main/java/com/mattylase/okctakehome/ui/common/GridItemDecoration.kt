package com.mattylase.okctakehome.ui.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(spacing: Float, val gridWidth: Int): RecyclerView.ItemDecoration() {

    private val spacingValue: Int
    private val halfSpacingValue: Int

    init {
        spacingValue = spacing.toInt()
        halfSpacingValue = spacingValue / 2
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        when (position % gridWidth) {
            0 ->  {
                outRect.left = spacingValue
                outRect.right = halfSpacingValue
            }
            gridWidth - 1 -> {
                outRect.left = halfSpacingValue
                outRect.right = spacingValue
            }
            else -> {
                outRect.left = halfSpacingValue
                outRect.right = halfSpacingValue
            }
        }

        when (position) {
            in 0 until gridWidth -> {
                outRect.top = spacingValue
                outRect.bottom = halfSpacingValue
            }
            else -> {
                outRect.top = halfSpacingValue
                outRect.bottom = halfSpacingValue
            }
        }
    }
}