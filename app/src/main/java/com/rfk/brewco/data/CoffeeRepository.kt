package com.rfk.brewco.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rfk.brewco.data.cart.Cart
import com.rfk.brewco.data.user.User
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CoffeeRepository(private val coffeeDao: CoffeeDao, private val executor: ExecutorService) {

    fun getCoffee(): LiveData<PagedList<Coffee>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(coffeeDao.getCoffee(), config).build()
    }

    fun getCoffeeById(id: Int): LiveData<Coffee> {
        return coffeeDao.getCoffeeById(id)
    }

    fun getCart(): LiveData<PagedList<Cart>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(coffeeDao.getCart(), config).build()
    }

    fun insertCart(newCart: Cart) {
        val callable = Callable { coffeeDao.insertCart(newCart) }
        val execute = executor.submit(callable)
        return execute.get()
    }

    fun deleteCart(cart: Cart) {
        executor.execute {
            coffeeDao.deleteCart(cart)
        }
    }

//    fun getUser(id: Int): LiveData<User> {
//        return coffeeDao.getUser(id)
//    }
//
//    fun updateUser(user: User) {
//        executor.execute {
//            coffeeDao.updateUser(user)
//        }
//    }

    companion object {
        @Volatile
        private var instance: CoffeeRepository? = null

        fun getInstance(context: Context): CoffeeRepository {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val database = CoffeeDatabase.getInstance(context)
                    instance = CoffeeRepository(
                        database.CoffeeDao(),
                        Executors.newSingleThreadExecutor()
                    )
                }
                return instance as CoffeeRepository
            }
        }
    }
}