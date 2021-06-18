package com.example.dscroomapp.respository

import com.example.dscroomapp.entities.UserItem
import com.example.dscroomapp.local.UserDao
import com.example.dscroomapp.remote.UserItemRemoteDataSource
import com.example.dscroomapp.utils.performGetOperation
import com.google.gson.JsonArray
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDataSource: UserItemRemoteDataSource,
    private val localDataSource: UserDao
) {
    fun getUser() = performGetOperation(
        databaseQuery = {localDataSource.getUsers()},
        networkCall = {remoteDataSource.getUsers()},
        saveCallResult = {localDataSource.insertAllUsers(getCleanData(it))}
    )

    private fun getCleanData(jsonArray: JsonArray): ArrayList<UserItem> {
        val array = arrayListOf<UserItem>()
        jsonArray.forEach {
            val id = it.asJsonObject.get("id").asInt
            val name = it.asJsonObject.get("name").asString
            val age = it.asJsonObject.get("age").asInt
            val userItem = UserItem(id, name, age)
            array.add(userItem)
        }
        return array
    }
}