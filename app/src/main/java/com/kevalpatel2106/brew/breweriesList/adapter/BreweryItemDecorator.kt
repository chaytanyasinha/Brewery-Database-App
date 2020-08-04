package com.kevalpatel2106.brew.breweriesList.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.brew.R

class BreweryItemDecorator(context: Context) : RecyclerView.ItemDecoration() {

    private val spacing by lazy(LazyThreadSafetyMode.NONE) {
        context.resources.getDimensionPixelSize(R.dimen.spacing_micro)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        outRect.left = spacing * 2
        outRect.right = spacing * 2
        outRect.bottom = spacing
        outRect.top = if (position == 0) spacing * 2 else spacing
    }
}
