package com.example.shoppingapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var shopListRecyclerView: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recyclerViewAdapter: ShopItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setOnClickListener()
        setOnLongClickListener()
        setOnSwipeListener()

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.shopList.observe(this) {
            recyclerViewAdapter.shopList = it
        }
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = ShopItemAdapter()
        shopListRecyclerView = findViewById(R.id.recyclerView)
        shopListRecyclerView.adapter = recyclerViewAdapter
        shopListRecyclerView.recycledViewPool.setMaxRecycledViews(ShopItemAdapter.SHOP_ITEM_ENABLED, ShopItemAdapter.POOL_SIZE)
        shopListRecyclerView.recycledViewPool.setMaxRecycledViews(ShopItemAdapter.SHOP_ITEM_DISABLED, ShopItemAdapter.POOL_SIZE)
    }

    private fun setOnClickListener() {
        recyclerViewAdapter.onClickListener = {
            Log.d("OnClick", it.toString())
        }
    }

    private fun setOnLongClickListener() {
        recyclerViewAdapter.onLongClickListener = {
            mainViewModel.changeEnabledState(it)
        }
    }

    private fun setOnSwipeListener() {
        val callback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var item = recyclerViewAdapter.shopList[viewHolder.adapterPosition]
                mainViewModel.deleteShopItem(item)
            }
        }

        var itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(shopListRecyclerView)
    }
}