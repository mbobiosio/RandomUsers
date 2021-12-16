package com.test.randomusers.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.test.randomusers.data.local.RandomUserDatabase
import com.test.randomusers.data.model.RemoteKeys
import com.test.randomusers.data.model.User
import com.test.randomusers.data.remote.ApiService
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import javax.inject.Inject

@ExperimentalPagingApi
class UserMediator @Inject constructor(
    private val apiService: ApiService,
    private val randomUserDatabase: RandomUserDatabase
) : RemoteMediator<Int, User>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, User>): MediatorResult {

        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> return pageKeyData
            else -> pageKeyData as Int
        }

        try {
            val response = apiService.getUsers(page)
            val isEndOfList =
                !response.isSuccessful || response.body() == null || response.body()?.results?.isEmpty() == true
            randomUserDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    randomUserDatabase.remoteKeysDao().clearRemoteKeys()
                    randomUserDatabase.userDao().clearAllUsers()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.body()?.results?.map {
                    RemoteKeys(id = it.id?.value, prevKey = prevKey, nextKey = nextKey)
                }
                if (keys != null)
                    randomUserDatabase.remoteKeysDao().insertAllRemoteKeys(keys)
                if (response.body() != null && response.body()?.results?.isNotEmpty() == true)
                    randomUserDatabase.userDao().insertAllUsers(response.body()!!.results)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    /**
     * this returns the page key or the final end of list success result
     */
    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, User>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                    ?: throw InvalidObjectException("Invalid state, key should not be null")
                //end of list condition reached
                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, User>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { user ->
                user.let { it.id?.value?.let { id -> randomUserDatabase.remoteKeysDao().getRemoteKeys(id) } }
            }
    }

    /**
     * get the first remote key inserted which had the data
     */
    private suspend fun getFirstRemoteKey(state: PagingState<Int, User>): RemoteKeys? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { user ->
                user.let { it.id?.value?.let { id -> randomUserDatabase.remoteKeysDao().getRemoteKeys(id) } }
            }
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getClosestRemoteKey(state: PagingState<Int, User>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id.let { id ->
                id?.value?.let { randomUserDatabase.remoteKeysDao().getRemoteKeys(it) }
            }
        }
    }

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
    }
}