package com.example.shoppingapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingapp.data.ShoppingListRepositoryImpl
import com.example.shoppingapp.domain.DeleteItemUseCase
import com.example.shoppingapp.domain.EditItemUseCase
import com.example.shoppingapp.domain.GetListUseCase
import com.example.shoppingapp.domain.ShopItem
import kotlin.random.Random

class MainViewModel: ViewModel() {

    private val repository = ShoppingListRepositoryImpl

    init {
        repeat(1000) {
        ShoppingListRepositoryImpl.addItem(
            ShopItem("Element", it, Random.nextBoolean())
        )}
    }

    val getListUseCase = GetListUseCase(repository)
    val deleteItemUseCase = DeleteItemUseCase(repository)
    val editItemUseCase = EditItemUseCase(repository)

    var shopList = getListUseCase.getList()

    fun deleteShopItem(item: ShopItem) {
        deleteItemUseCase.deleteItem(item)
    }

    fun changeEnabledState(item: ShopItem) {
        val item = item.copy(enabled = !item.enabled)
        editItemUseCase.editItem(item)
    }
}