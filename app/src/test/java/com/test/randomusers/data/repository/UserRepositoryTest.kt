package com.test.randomusers.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.randomusers.data.coroutines.DispatcherProvider
import com.test.randomusers.data.local.RandomUserDatabase
import com.test.randomusers.data.local.dao.UserDao
import com.test.randomusers.data.remote.ApiService
import com.test.randomusers.utils.MainCoroutineRule
import com.test.randomusers.utils.TestUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    @get:Rule
    val mainCoroutinesRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: UserRepositoryImpl
    private lateinit var randomUserDatabase: RandomUserDatabase
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        randomUserDatabase = mock(RandomUserDatabase::class.java)
        apiService = mock(ApiService::class.java)
        dispatcherProvider = mock(DispatcherProvider::class.java)
        repository = UserRepositoryImpl(dispatcherProvider, apiService, randomUserDatabase)
    }

    @Test
    fun `get user emits flow user`() = runBlocking {

        //Stub out database to return a mock dao.
        val dao = mock(UserDao::class.java)
        `when`(randomUserDatabase.userDao()).thenReturn(dao)

        //Stub out dao to return a Users
        val user = TestUtil.createUser()
        `when`(dao.getUserByEmail("jessika.nickel@example.com")).thenReturn(user)

        //Method under test.
        val flow = repository.getUser("jessika.nickel@example.com")

        //Verify data in the result
        flow.collect {
            assertThat(it, `is`(user))
        }
    }
}