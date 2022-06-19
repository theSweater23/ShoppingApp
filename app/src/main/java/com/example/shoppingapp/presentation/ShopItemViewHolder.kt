package com.example.shoppingapp.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R

class ShopItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val itemName = view.findViewById<TextView>(R.id.item_name)
    val itemCount = view.findViewById<TextView>(R.id.item_count)
}