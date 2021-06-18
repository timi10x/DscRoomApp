package com.example.dscroomapp.remote

import javax.inject.Inject

class UserItemRemoteDataSource @Inject constructor(
    private val userService: UserService
) : BaseDataSource() {
    suspend fun getUsers() = getResult {
        userService.getAllUsers()
    }
}