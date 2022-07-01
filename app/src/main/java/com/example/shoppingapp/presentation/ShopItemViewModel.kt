package com.example.shoppingapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingapp.data.ShoppingListRepositoryImpl
import com.example.shoppingapp.domain.AddItemUseCase
import com.example.shoppingapp.domain.EditItemUseCase
import com.example.shoppingapp.domain.GetItemUseCase
import com.example.shoppingapp.domain.ShopItem

class ShopItemViewModel: ViewModel() {

    private val repository = ShoppingListRepositoryImpl
    private val addItemUseCase = AddItemUseCase(repository)
    private val editItemUseCase = EditItemUseCase(repository)
    private val getItemUseCase = GetItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _closeScreen = MutableLiveData<Unit>()
    val closeScreen: LiveData<Unit>
        get() = _closeScreen

    fun getItem(id: Int) {
        val item = getItemUseCase.getItem(id)
        _shopItem.value = item
    }

    fun editItemUseCase(name: String?, count: String?) {
        val name = parseName(name)
        val count = parseCount(count)

        if (validateValues(name, count)) {
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                editItemUseCase.editItem(item)
            }
        }
        _closeScreen.value = Unit
    }

    fun addItem(name: String?, count: String?) {
        val name = parseName(name)
        val count = parseCount(count)

        if (validateValues(name, count)) {
            addItemUseCase.addItem(ShopItem(name, count, true))
        }
        _closeScreen.value = Unit
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {
        return try {
            count?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateValues(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            return false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            return false
        }
        return result
    }

    fun resetNameError() {
        _errorInputName.value = false
    }

    fun resetCountError() {
        _errorInputCount.value = false
    }
}