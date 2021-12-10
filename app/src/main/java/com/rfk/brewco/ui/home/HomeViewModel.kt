package com.rfk.brewco.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rfk.brewco.data.Coffee
import com.rfk.brewco.data.CoffeeRepository

class HomeViewModel(private val coffeeRepository: CoffeeRepository) : ViewModel() {
    val coffee: LiveData<PagedList<Coffee>> = coffeeRepository.getCoffee()
}