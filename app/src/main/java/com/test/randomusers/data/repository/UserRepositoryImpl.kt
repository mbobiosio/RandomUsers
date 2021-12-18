package com.test.randomusers.data.repository

import androidx.lifecycle.liveData
import com.test.randomusers.data.coroutines.DispatcherProvider
import com.test.randomusers.data.local.RandomUserDatabase
import com.test.randomusers.data.networkresource.NetworkStatus
import com.test.randomusers.data.networkresource.safeApiCall
import com.test.randomusers.data.remote.ApiService
import javax.inject.Inject

private const val LOADING_TIMEOUT = 20_000L

class UserRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val apiService: ApiService,
    private val randomUserDatabase: RandomUserDatabase?
) : UserRepository {

    override suspend fun getUsersFromRemote() = liveData(dispatcherProvider.io(), LOADING_TIMEOUT) {
        emit(NetworkStatus.Loading(randomUserDatabase?.userDao()?.getAllUsers()))

        val response = safeApiCall { apiService.getUsers(URL) }
        if (response is NetworkStatus.Success) {
            response.data?.results?.let {
                randomUserDatabase?.userDao()?.clearAllUsers()
                randomUserDatabase?.userDao()?.insertAllUsers(it)
                emit(NetworkStatus.Success(randomUserDatabase?.userDao()?.getAllUsers()))
            }
        } else emit(NetworkStatus.Error(response.message, randomUserDatabase?.userDao()?.getAllUsers()))
    }

    companion object {
        const val URL = "https://randomuser.me/api/?results=200"
    }
}