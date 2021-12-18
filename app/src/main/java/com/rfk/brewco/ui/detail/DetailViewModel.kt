package com.rfk.brewco.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rfk.brewco.data.Coffee
import com.rfk.brewco.data.CoffeeRepository
import com.rfk.brewco.data.cart.Cart

class DetailViewModel(private val coffeeRepository: CoffeeRepository): ViewModel() {
    private var id: Int = 0

    fun setSelectedId(id: Int) {
        this.id = id
    }

    fun getCoffee() : LiveData<Coffee> = coffeeRepository.getCoffeeById(id)

    fun insertCart(cart: Cart) = coffeeRepository.insertCart(cart)

    var total = MutableLiveData<Int>()
    var price = 0
    var shot = 0
    var type = 0
    var size = 0

    fun currentShot(shot: Int) {
        this.shot = shot
    }

    fun currentType(type: Int) {
        this.type = type
    }

    fun currentSize(size: Int) {
        this.size = size
    }

    fun calculate() {
        total.value = price + shot + type + size
    }

    fun calculateTotalPrice(num: Int, price: Int) {
        this.price = num * price
    }
}