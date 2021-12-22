package com.rfk.brewco.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rfk.brewco.data.CoffeeRepository
import com.rfk.brewco.data.cart.Cart
import com.rfk.brewco.data.history.History

class CartViewModel(private val coffeeRepository: CoffeeRepository) : ViewModel() {
    val cart: LiveData<PagedList<Cart>> = coffeeRepository.getCart()

    fun insertCart(cart: Cart) {
        coffeeRepository.insertCart(cart)
    }

    fun deleteCart(cart: Cart) {
        coffeeRepository.deleteCart(cart)
    }

    fun deleteAllCart() {
        coffeeRepository.deleteAllCart()
    }

    fun insertHistory(history: History) {
        coffeeRepository.insertHistory(history)
    }
}