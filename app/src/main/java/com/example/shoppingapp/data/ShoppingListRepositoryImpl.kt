package com.example.shoppingapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppingapp.domain.ShopItem
import com.example.shoppingapp.domain.ShoppingListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object ShoppingListRepositoryImpl: ShoppingListRepository {

    private val shopListLiveData = MutableLiveData<List<ShopItem>>()

    private val shopList = sortedSetOf<ShopItem>({o1,o2 -> o1.id.compareTo(o2.id)})

    private var itemId: Int = 0

    override fun addItem(item: ShopItem) {
        if(item.id == ShopItem.UNDEFINED_ID) {
            item.id = itemId++
        }
        shopList.add(item)
        updateList()
    }

    override fun deleteItem(item: ShopItem) {
        shopList.remove(item)
        updateList()
    }

    override fun editItem(shopItem: ShopItem) {
        val item = getItem(shopItem.id)
        shopList.remove(item)
        addItem(shopItem)
    }

    override fun getItem(id: Int): ShopItem {
        return shopList.find {
            it.id == id
        } ?: throw RuntimeException("Item with id $id not found")
    }

    override fun getList(): LiveData<List<ShopItem>> {
        return shopListLiveData
    }

    fun updateList() {
        shopListLiveData.value = shopList.toList()
    }
}