package com.test.randomusers.data.repository

import androidx.paging.PagingData
import com.test.randomusers.data.coroutines.DispatcherProvider
import com.test.randomusers.data.model.User
import com.test.randomusers.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getUsersFlowDb(): Flow<PagingData<User>>? {
        return null
    }
}