package com.test.randomusers.di.module

import androidx.paging.ExperimentalPagingApi
import com.test.randomusers.data.coroutines.DefaultDispatcherProvider
import com.test.randomusers.data.coroutines.DispatcherProvider
import com.test.randomusers.data.local.RandomUserDatabase
import com.test.randomusers.data.remote.ApiService
import com.test.randomusers.data.repository.UserRepository
import com.test.randomusers.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalPagingApi
@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    internal fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

    @Singleton
    @Provides
    fun provideUserRepository(
        dispatcherProvider: DispatcherProvider,
        apiService: ApiService,
        randomUserDatabase: RandomUserDatabase
    ): UserRepository {
        return UserRepositoryImpl(dispatcherProvider, apiService, randomUserDatabase)
    }
}