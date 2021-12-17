package com.test.randomusers.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.randomusers.data.model.User
import com.test.randomusers.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    suspend fun fetchRandomUsers(): Flow<PagingData<User>>? = repository.getUsersFlowDb()?.cachedIn(viewModelScope)
}