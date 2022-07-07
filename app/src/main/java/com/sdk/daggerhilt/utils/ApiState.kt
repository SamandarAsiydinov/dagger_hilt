package com.sdk.daggerhilt.utils

import com.sdk.daggerhilt.model.User

sealed class ApiState {
    object Loading: ApiState()
    data class Error(val error: String): ApiState()
    data class Success(val users: List<User>): ApiState()
}
