package com.test.randomusers.ui.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.test.randomusers.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _userListLiveData = MutableLiveData<Unit>()
    val userListLiveData = _userListLiveData.switchMap {
        liveData {
            emitSource(repository.getUsersFromRemote())
        }
    }

    private val _userLiveData = MutableLiveData<String>()
    val userLiveData = _userLiveData.switchMap {
        liveData {
            emitSource(repository.getUserByIdFromDb(it))
        }
    }

    fun fetchUsers() {
        _userListLiveData.value = Unit
    }

    fun getUser(email: String) {
        _userLiveData.value = email
    }
}