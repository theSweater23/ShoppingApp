package com.example.shoppingapp.domain

class DeleteItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun deleteItem(item: ShopItem) {
        shoppingListRepository.deleteItem(item)
    }
}