package com.example.dscroomapp.remote

import com.google.gson.JsonArray
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("users")
    suspend fun getAllUsers() : Response<JsonArray>
}