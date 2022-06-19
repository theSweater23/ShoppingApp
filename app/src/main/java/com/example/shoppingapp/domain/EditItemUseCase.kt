package com.example.shoppingapp.domain

class EditItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun editItem(item: ShopItem) {
        shoppingListRepository.editItem(item)
    }
}