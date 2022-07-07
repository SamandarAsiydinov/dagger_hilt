package com.sdk.daggerhilt.network

import com.sdk.daggerhilt.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}