package com.sdk.daggerhilt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.daggerhilt.repository.UserRepository
import com.sdk.daggerhilt.utils.ApiState
import com.sdk.daggerhilt.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository, private val networkHelper: NetworkHelper) : ViewModel() {

    private val _response: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Loading)
    val response: StateFlow<ApiState> get() = _response

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                _response.value = ApiState.Loading
                userRepository.getRemoteUsers().catch {
                    _response.value = ApiState.Error(it.stackTraceToString())
                }.collect {
                    if (it.isSuccessful) {
                        userRepository.saveUsers(it.body()!!)
                        _response.value = ApiState.Success(userRepository.getLocalUsers())
                    } else {
                        _response.value = ApiState.Error(it.errorBody().toString())
                    }
                }
            }
        } else {
            _response.value = ApiState.Error("No internet connection")
        }
    }
}