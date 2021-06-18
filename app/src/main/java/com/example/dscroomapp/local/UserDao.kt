package com.example.dscroomapp.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dscroomapp.entities.UserItem

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getUsers() : LiveData<List<UserItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: List<UserItem>)

}