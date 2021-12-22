package com.rfk.brewco.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.rfk.brewco.data.cart.Cart
import com.rfk.brewco.data.history.History

@Dao
interface CoffeeDao {
    @Query("SELECT * FROM coffee")
    fun getCoffee(): DataSource.Factory<Int, Coffee>

    @Query("SELECT * FROM coffee WHERE id = :id")
    fun getCoffeeById(id: Int): LiveData<Coffee>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg coffee: Coffee)

    //Cart
    @Query("SELECT * FROM cart")
    fun getCart(): DataSource.Factory<Int, Cart>

    @Query("SELECT * FROM cart WHERE id = :id ")
    fun getCartById(id: Int): LiveData<Cart>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCart(cart: Cart)

    @Delete
    fun deleteCart(cart: Cart)

    @Query("DELETE FROM cart")
    fun deleteAllCart()

    //History
    @Query("SELECT * FROM history ORDER BY id DESC")
    fun getHistory(): DataSource.Factory<Int, History>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertHistory(history: History)
}