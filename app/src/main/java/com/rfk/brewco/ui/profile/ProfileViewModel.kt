package com.rfk.brewco.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rfk.brewco.data.CoffeeRepository
import com.rfk.brewco.data.user.User

class ProfileViewModel(private val coffeeRepository: CoffeeRepository) : ViewModel() {
    private var id: Int = 0

    fun setSelectedId(id: Int) {
        this.id = id
    }

//    fun getUser() : LiveData<User> = coffeeRepository.getUser(id)
}