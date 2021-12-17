package com.test.randomusers.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.randomusers.data.local.dao.UserDao
import com.test.randomusers.data.model.User


@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RandomUserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}