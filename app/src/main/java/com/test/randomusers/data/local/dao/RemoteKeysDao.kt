package com.test.randomusers.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.randomusers.data.model.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(remoteKeyDao: List<RemoteKeys>)

    @Query("select * from remotekeys where email = :email")
    suspend fun getRemoteKeys(email: String): RemoteKeys?

    @Query("delete from remotekeys")
    fun clearRemoteKeys()
}