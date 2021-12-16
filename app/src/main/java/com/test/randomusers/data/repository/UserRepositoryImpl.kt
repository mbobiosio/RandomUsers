package com.test.randomusers.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.test.randomusers.data.coroutines.DispatcherProvider
import com.test.randomusers.data.local.RandomUserDatabase
import com.test.randomusers.data.model.User
import com.test.randomusers.data.paging.UserMediator
import com.test.randomusers.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalPagingApi
class UserRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val apiService: ApiService,
    private val randomUserDatabase: RandomUserDatabase?
) : UserRepository {

    override suspend fun getUsersFlowDb(): Flow<PagingData<User>> =
        withContext(dispatcherProvider.io()) {
            if (randomUserDatabase == null) throw IllegalStateException("Database is not initialized")

            val pagingSourceFactory =
                { randomUserDatabase.userDao().getAllUsers() }

            Pager(
                config = getDefaultPageConfig(),
                pagingSourceFactory = pagingSourceFactory,
                remoteMediator = UserMediator(apiService, randomUserDatabase)
            ).flow
        }

    /**
     * This is to define the page size in the [PagingConfig]
     */
    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}