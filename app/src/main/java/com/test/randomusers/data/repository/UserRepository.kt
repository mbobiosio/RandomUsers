package com.test.randomusers.data.repository

import androidx.paging.PagingData
import com.test.randomusers.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUsersFlowDb(): Flow<PagingData<User>>?
}