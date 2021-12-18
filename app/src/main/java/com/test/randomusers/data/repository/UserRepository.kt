package com.test.randomusers.data.repository

import androidx.lifecycle.LiveData
import com.test.randomusers.data.model.User
import com.test.randomusers.data.networkresource.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsersFromRemote(): LiveData<NetworkStatus<List<User>>>
    suspend fun getUser(email: String): Flow<User?>
}