package com.test.randomusers.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.test.randomusers.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: List<User>)

    @Transaction
    @Query("select * from user")
    fun getAllUsers(): PagingSource<Int, User>

    @Query("select * from user where _id = :id")
    fun getAllUsersById(id: Int): User

    @Query("delete from user")
    fun clearAllUsers()
}