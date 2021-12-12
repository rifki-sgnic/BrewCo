package com.rfk.brewco.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rfk.brewco.data.Coffee
import com.rfk.brewco.data.CoffeeRepository

class DetailViewModel(private val coffeeRepository: CoffeeRepository): ViewModel() {
    private var id: Int = 0

    fun setSelectedId(id: Int) {
        this.id = id
    }

    fun getCoffee() : LiveData<Coffee> = coffeeRepository.getCoffeeById(id)

    var price = 0

}