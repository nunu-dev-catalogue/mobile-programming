package dev.nunu.mobile.konkuk.room

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    init {
        viewModelScope.launch {
            runCatching {
                repository.getAll()
            }.onSuccess {
                _users.value = it
            }.onFailure {
                // Handle error
            }
        }
    }

    fun insert(user: User) {
        viewModelScope.launch {
            runCatching {
                repository.insertAll(user)
            }.onSuccess {
                _users.value = repository.getAll()
            }.onFailure {
                // Handle error
            }
        }
    }

    fun delete(user: User) {
        viewModelScope.launch {
            runCatching {
                repository.delete(user)
            }.onSuccess {
                _users.value = repository.getAll()
            }.onFailure {
                // Handle error
            }
        }
    }

    companion object {
        val FACTORY: (Context) -> ViewModelProvider.Factory = { context ->
            viewModelFactory {
                initializer {
                    UserViewModel(DefaultUserRepository(AppDatabase.getInstance(context).userDao()))
                }
            }
        }
    }
}