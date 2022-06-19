package com.example.shoppingapp.domain

class AddItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun addItem(item: ShopItem) {
        shoppingListRepository.addItem(item)
    }
}