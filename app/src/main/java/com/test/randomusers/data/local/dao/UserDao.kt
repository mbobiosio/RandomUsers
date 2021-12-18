package com.test.randomusers.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.randomusers.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: List<User>)

    @Query("select * from user")
    suspend fun getAllUsers(): List<User>?

    @Query("select * from user where email = :email")
    fun getUserByEmail(email: String): User

    @Query("delete from user")
    fun clearAllUsers()
}