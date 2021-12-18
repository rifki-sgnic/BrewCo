package com.rfk.brewco.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rfk.brewco.data.CoffeeRepository
import com.rfk.brewco.data.cart.Cart

class CartViewModel(private val coffeeRepository: CoffeeRepository) : ViewModel() {
    val cart: LiveData<PagedList<Cart>> = coffeeRepository.getCart()
}