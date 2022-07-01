package com.example.shoppingapp.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.ShopItem
import java.lang.RuntimeException

class ShopItemActivity : AppCompatActivity() {

    private var shopItemId = ShopItem.UNDEFINED_ID

    private var screenMode = SCREEN_MODE_UNDEFINED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        screenMode = intent.getStringExtra(EXTRA_MODE).toString()
        shopItemId = intent.getIntExtra("EXTRA_MODE_EDIT_ID", ShopItem.UNDEFINED_ID)

        val fragment = when(screenMode) {
            MODE_ADD -> ShopItemFragment.newInstanceAddShopItem()
            MODE_EDIT -> ShopItemFragment.newInstanceEditShopItem(shopItemId)
            else -> throw RuntimeException("Unknown mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .commit()
    }

    /*private fun launchAddMode() {
        saveButton.setOnClickListener {
            shopItemViewModel.addItem(etName.text.toString(), etCount.text.toString())
        }
    }*/

    /*private fun launchEditMode() {
        shopItemViewModel.getItem(shopItemId)
        shopItemViewModel.shopItem.observe(this) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        saveButton.setOnClickListener {
            shopItemViewModel.editItemUseCase(etName.text.toString(), etCount.text.toString())
        }
    }*/

    companion object {
        private const val EXTRA_MODE = "EXTRA_MODE"
        private const val MODE_ADD = "MODE_ADD"
        private const val MODE_EDIT = "MODE_EDIT"
        private const val EXTRA_MODE_EDIT_ID = "EXTRA_MODE_EDIT_ID"
        private const val SCREEN_MODE_UNDEFINED = "SCREEN_MODE_UNDEFINED"

        fun intentAdd(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }

        fun intentEdit(context: Context, itemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_MODE_EDIT_ID, itemId)
            return intent
        }
    }
}