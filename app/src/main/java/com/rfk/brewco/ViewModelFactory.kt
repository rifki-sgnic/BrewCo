package com.rfk.brewco

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rfk.brewco.data.CoffeeRepository
import com.rfk.brewco.ui.cart.CartViewModel
import com.rfk.brewco.ui.detail.DetailViewModel
import com.rfk.brewco.ui.history.HistoryViewModel
import com.rfk.brewco.ui.home.HomeViewModel

class ViewModelFactory private constructor(private val coffeeRepository: CoffeeRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(coffeeRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(coffeeRepository) as T
            }
            modelClass.isAssignableFrom(CartViewModel::class.java) -> {
                CartViewModel(coffeeRepository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(coffeeRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    CoffeeRepository.getInstance(context)
                )
            }
    }
}