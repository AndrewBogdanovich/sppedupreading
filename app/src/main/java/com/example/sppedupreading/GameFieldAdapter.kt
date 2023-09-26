package com.example.sppedupreading

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.sqrt

class GameFieldAdapter : RecyclerView.Adapter<GameFieldViewHolder>() {
    private val adapterData = mutableListOf<Int>()
    private var itemsSize: Int = 0
    private var screenWidth: Int = 0
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameFieldViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        val lp = view.layoutParams
        val rows = sqrt(itemsSize.toDouble())
        lp.height = screenWidth / rows.toInt()
        return GameFieldViewHolder(view)
    }

    override fun getItemCount(): Int = itemsSize

    override fun onBindViewHolder(holder: GameFieldViewHolder, position: Int) {
        holder.fieldTv.text = adapterData[position].toString()
    }

    fun setupData(data: List<Int>) {
        adapterData.apply {
            clear()
            addAll(data)
        }
        itemsSize = adapterData.size
    }

    fun setScreenWidth(width: Int) {
        screenWidth = width
    }
}

class GameFieldViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val fieldTv: TextView = view.findViewById(R.id.field_tv)
}
