package com.rfk.brewco.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoffeeDao {
    @Query("SELECT * FROM coffee")
    fun getCoffee(): DataSource.Factory<Int, Coffee>

    @Query("SELECT * FROM coffee WHERE id = :id ")
    fun getCoffeeById(id: Int): LiveData<Coffee>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg tasks: Coffee)
}