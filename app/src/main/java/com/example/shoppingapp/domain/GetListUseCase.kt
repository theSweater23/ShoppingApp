package com.example.shoppingapp.domain

import androidx.lifecycle.LiveData

class GetListUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun getList(): LiveData<List<ShopItem>> {
        return shoppingListRepository.getList()
    }
}