package com.example.shoppingapp.domain

import androidx.lifecycle.LiveData

interface ShoppingListRepository {

    fun addItem(item: ShopItem)

    fun deleteItem(item: ShopItem)

    fun editItem(shopItem: ShopItem)

    fun getItem(id: Int): ShopItem

    fun getList(): LiveData<List<ShopItem>>
}