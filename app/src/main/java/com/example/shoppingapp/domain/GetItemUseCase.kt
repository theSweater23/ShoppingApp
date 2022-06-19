package com.example.shoppingapp.domain

class GetItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun getItem(id: Int): ShopItem {
        return shoppingListRepository.getItem(id)
    }
}