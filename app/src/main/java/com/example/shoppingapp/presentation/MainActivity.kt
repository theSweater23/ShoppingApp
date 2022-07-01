package com.example.shoppingapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var shopListRecyclerView: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recyclerViewAdapter: ShopItemAdapter

    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shopItemContainer = findViewById(R.id.shop_item_container)

        setupRecyclerView()
        setOnClickListener()
        setOnLongClickListener()
        setOnSwipeListener()
        setupAddButton()

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.shopList.observe(this) {
            recyclerViewAdapter.submitList(it)
        }
    }

    private fun setupAddButton() {
        val addBtn = findViewById<FloatingActionButton>(R.id.float_action_btn)
        addBtn.setOnClickListener {
            if (isPortraitOrientation())
                startActivity(ShopItemActivity.intentAdd(this))
            else
                launchFragment(ShopItemFragment.newInstanceAddShopItem())
        }
    }

    private fun isPortraitOrientation() = shopItemContainer == null

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .commit()
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
            if (isPortraitOrientation()) {
                startActivity(ShopItemActivity.intentEdit(this, it.id))
            } else {
                launchFragment(ShopItemFragment.newInstanceEditShopItem(it.id))
            }
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
                var item = recyclerViewAdapter.currentList[viewHolder.adapterPosition]
                mainViewModel.deleteShopItem(item)
            }
        }

        var itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(shopListRecyclerView)
    }
}