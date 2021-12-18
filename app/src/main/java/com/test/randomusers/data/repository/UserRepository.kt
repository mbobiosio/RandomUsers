package com.test.randomusers.data.repository

import androidx.lifecycle.LiveData
import com.test.randomusers.data.model.User
import com.test.randomusers.data.networkresource.NetworkStatus

interface UserRepository {
    suspend fun getUsersFromRemote(): LiveData<NetworkStatus<List<User>>>
}