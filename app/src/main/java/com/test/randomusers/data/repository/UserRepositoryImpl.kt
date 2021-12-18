package com.test.randomusers.data.repository

import androidx.lifecycle.liveData
import com.test.randomusers.data.coroutines.DispatcherProvider
import com.test.randomusers.data.local.RandomUserDatabase
import com.test.randomusers.data.networkresource.NetworkStatus
import com.test.randomusers.data.networkresource.safeApiCall
import com.test.randomusers.data.remote.ApiService
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val LOADING_TIMEOUT = 20_000L

class UserRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val apiService: ApiService,
    private val randomUserDatabase: RandomUserDatabase?
) : UserRepository {

    override suspend fun getUsersFromRemote() = liveData(dispatcherProvider.io(), LOADING_TIMEOUT) {
        val dataFromDb = randomUserDatabase?.userDao()?.getAllUsers()
        emit(NetworkStatus.Loading(dataFromDb))

        if (dataFromDb.isNullOrEmpty()) {
            val response = safeApiCall { apiService.getUsers(URL) }
            if (response is NetworkStatus.Success) {
                response.data?.results?.let {
                    randomUserDatabase?.userDao()?.insertAllUsers(it)
                    emit(NetworkStatus.Success(randomUserDatabase?.userDao()?.getAllUsers()))
                }
            } else emit(NetworkStatus.Error(response.message, randomUserDatabase?.userDao()?.getAllUsers()))
        }
    }

    /**
     * For testing ONLY
     */
    override suspend fun getUser(email: String) = flow {
        val user = randomUserDatabase?.userDao()?.getUserByEmail(email)
        emit(user)
    }.flowOn(dispatcherProvider.default())

    companion object {
        const val URL = "https://randomuser.me/api/?results=200"
    }
}