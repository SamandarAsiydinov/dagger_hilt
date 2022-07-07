package com.sdk.daggerhilt.repository

import com.sdk.daggerhilt.database.UserDao
import com.sdk.daggerhilt.model.User
import com.sdk.daggerhilt.network.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {
    suspend fun getRemoteUsers() = flow { emit(apiService.getUsers()) }

    suspend fun saveUsers(users: List<User>) = userDao.saveAllUsers(users)
    suspend fun saveUser(user: User) = userDao.saveUser(user)
    suspend fun deleteAllUsers() = userDao.deleteAllUsers()
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
    suspend fun getLocalUsers() = userDao.getUsers()
}