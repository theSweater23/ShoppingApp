package com.example.shoppingapp.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.ShopItem
import java.lang.RuntimeException

class ShopItemAdapter: RecyclerView.Adapter<ShopItemAdapter.ShopItemViewHolder>() {

    var count = 0

    var onClickListener: ((ShopItem) -> Unit)? = null
    var onLongClickListener: ((ShopItem) -> Unit)? = null

    var shopList = listOf<ShopItem>()
    set(value) {
        val callback = ShopListDiff(shopList, value)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)
        field = value
    }

    class ShopItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val itemName = view.findViewById<TextView>(R.id.item_name)
        val itemCount = view.findViewById<TextView>(R.id.item_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        Log.d("VH", "View Holders: ${++count}")
        var shopItemLayout = when(viewType){
            SHOP_ITEM_ENABLED -> R.layout.shop_item_enabled
            SHOP_ITEM_DISABLED -> R.layout.shop_item_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(shopItemLayout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.itemName.text = shopItem.name
        holder.itemCount.text = shopItem.count.toString()

        holder.itemView.setOnClickListener {
            onClickListener?.invoke(shopItem)
        }
        holder.itemView.setOnLongClickListener {
            onLongClickListener?.invoke(shopItem)
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = shopList[position]
        return if(shopItem.enabled) {
            SHOP_ITEM_ENABLED
        } else {
            SHOP_ITEM_DISABLED
        }
    }

    override fun getItemCount(): Int = shopList.size

    companion object {
        const val SHOP_ITEM_ENABLED = 0
        const val SHOP_ITEM_DISABLED = 1
        const val POOL_SIZE = 10
    }
}