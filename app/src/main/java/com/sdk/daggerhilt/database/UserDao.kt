package com.sdk.daggerhilt.database

import androidx.room.*
import com.sdk.daggerhilt.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAllUsers(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveUser(user: User)

    @Query("SELECT * FROM User ORDER BY id ASC")
    suspend fun getUsers(): List<User>

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM User")
    suspend fun deleteAllUsers()

}