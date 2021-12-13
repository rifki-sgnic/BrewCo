package com.rfk.brewco.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.rfk.brewco.data.user.User

@Dao
interface CoffeeDao {
    @Query("SELECT * FROM coffee")
    fun getCoffee(): DataSource.Factory<Int, Coffee>

    @Query("SELECT * FROM coffee WHERE id = :id ")
    fun getCoffeeById(id: Int): LiveData<Coffee>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg coffee: Coffee)

    //User
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllUser(vararg user: User)

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM user WHERE id =:id")
    fun getUser(id: Int): LiveData<User>
}