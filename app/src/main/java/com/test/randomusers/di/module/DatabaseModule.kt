package com.test.randomusers.di.module

import android.content.Context
import androidx.room.Room
import com.test.randomusers.data.local.RandomUserDatabase
import com.test.randomusers.data.local.dao.RemoteKeysDao
import com.test.randomusers.data.local.dao.UserDao
import com.test.randomusers.utils.AppConstants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    /*
     * The method returns the Database object
     **/
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): RandomUserDatabase =
        Room.databaseBuilder(context, RandomUserDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration() // allows database to be cleared after upgrading version
            .build()

    /*
    * We need the respective dao modules.
    * For this, We need the RandomUserDatabase object
    * So we will define the providers for there here in this module.
    * */
    @Singleton
    @Provides
    fun provideUserDao(randomUserDatabase: RandomUserDatabase):
            UserDao = randomUserDatabase.userDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(randomUserDatabase: RandomUserDatabase):
            RemoteKeysDao = randomUserDatabase.remoteKeysDao()
}