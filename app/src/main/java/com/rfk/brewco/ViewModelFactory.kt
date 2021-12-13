package com.rfk.brewco

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rfk.brewco.data.CoffeeRepository
import com.rfk.brewco.ui.detail.DetailViewModel
import com.rfk.brewco.ui.home.HomeViewModel
import com.rfk.brewco.ui.profile.ProfileViewModel

class ViewModelFactory private constructor(private val coffeeRepository: CoffeeRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(coffeeRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(coffeeRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(coffeeRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    companion object {
        @Volatile
        private var instance : ViewModelFactory? = null

        fun getInstance(context: Context) : ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    CoffeeRepository.getInstance(context)
                )
            }
    }
}