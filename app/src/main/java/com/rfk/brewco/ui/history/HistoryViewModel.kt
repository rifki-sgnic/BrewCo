package com.rfk.brewco.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rfk.brewco.data.CoffeeRepository
import com.rfk.brewco.data.history.History

class HistoryViewModel(private val coffeeRepository: CoffeeRepository) : ViewModel() {
    val history: LiveData<PagedList<History>> = coffeeRepository.getHistory()
}