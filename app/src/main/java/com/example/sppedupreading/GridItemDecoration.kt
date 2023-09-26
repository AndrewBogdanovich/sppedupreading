package com.example.sppedupreading

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(private val context: Context, private val spanCount: Int) :
    RecyclerView.ItemDecoration() {

    private val dividerWidth = 2 // Set your desired border width here
    private val dividerColor = R.color.black // Replace with your desired color

    private val paint = Paint()

    init {
        paint.color = ContextCompat.getColor(context, dividerColor)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = dividerWidth.toFloat()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        if (column == 0) {
            outRect.left = 0
            outRect.right = dividerWidth / 2
        } else if (column == spanCount - 1) {
            outRect.left = dividerWidth / 2
            outRect.right = 0
        } else {
            outRect.left = dividerWidth / 2
            outRect.right = dividerWidth / 2
        }

        outRect.top = 0
        outRect.bottom = dividerWidth / 2
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        drawBorders(c, parent)
    }

    private fun drawBorders(canvas: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val left = child.left.toFloat()
            val right = child.right.toFloat()
            val top = child.top.toFloat()
            val bottom = child.bottom.toFloat()

            // Draw top border
            canvas.drawLine(left, top, right, top, paint)

            // Draw right border
            canvas.drawLine(right, top, right, bottom, paint)

            // Draw bottom border
            canvas.drawLine(left, bottom, right, bottom, paint)

            // Draw left border
            canvas.drawLine(left, top, left, bottom, paint)
        }
    }
}
