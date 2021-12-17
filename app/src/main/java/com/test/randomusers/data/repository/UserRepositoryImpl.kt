package com.test.randomusers.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.test.randomusers.data.coroutines.DispatcherProvider
import com.test.randomusers.data.local.RandomUserDatabase
import com.test.randomusers.data.model.User
import com.test.randomusers.data.networkresource.NetworkStatus
import com.test.randomusers.data.networkresource.safeApiCall
import com.test.randomusers.data.remote.ApiService
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val apiService: ApiService,
    private val randomUserDatabase: RandomUserDatabase?
) : UserRepository {

    override suspend fun getUsersFromRemote() = liveData {
        emit(NetworkStatus.Loading(randomUserDatabase?.userDao()?.getAllUsers()))

        withContext(dispatcherProvider.io()) {
            when (val response = safeApiCall { apiService.getUsers(URL) }) {
                is NetworkStatus.Success -> {
                    response.data?.results?.let {
                        randomUserDatabase?.userDao()?.insertAllUsers(it)
                        emit(NetworkStatus.Success(it))
                    }
                }
                is NetworkStatus.Error -> {
                    val loadDb = randomUserDatabase?.userDao()?.getAllUsers()
                    emit(NetworkStatus.Error(response.message, loadDb))
                }
            }
        }
    }

    override suspend fun getUserByIdFromDb(email: String?): LiveData<User> =
        withContext(dispatcherProvider.io()) { randomUserDatabase?.userDao()?.getUserById(email)!! }

    companion object {
        const val URL = "https://randomuser.me/api/?results=200"
    }
}