package com.test.randomusers.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.randomusers.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class ApiServiceTest : ApiAbstract<ApiService>() {

    @get: Rule
    val coroutinesRule = MainCoroutineRule()

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: ApiService

    @Before
    fun initService() {
        service = createService(ApiService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun getUsersFromNetwork() = runBlocking {
        enqueueResponse("random-user-response.json")
        val response = service.getUsers("https://randomuser.me/api/?results=1")
        val responseBody = requireNotNull(response.body())
        mockWebServer.takeRequest()

        val loaded = responseBody.results[0]
        assertThat(responseBody.info?.results, `is`(1))
        assertThat(loaded.fullName, `is`("Jessika Nickel"))
        assertThat(loaded.dob?.age, `is`(66))
        assertThat(loaded.gender, `is`("female"))
        assertThat(loaded.email, `is`("jessika.nickel@example.com"))
        assertThat(loaded.picture?.large, `is`("https://randomuser.me/api/portraits/women/50.jpg"))
    }
}