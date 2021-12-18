package com.test.randomusers.data.local.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.test.randomusers.data.local.RandomUserDatabase
import com.test.randomusers.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.io.IOException

abstract class BaseDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get: Rule
    val coroutinesRule = MainCoroutineRule()

    lateinit var db: RandomUserDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RandomUserDatabase::class.java)
            .setTransactionExecutor(coroutinesRule.testDispatcher.asExecutor())
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}