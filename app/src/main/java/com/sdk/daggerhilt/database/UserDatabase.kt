package com.sdk.daggerhilt.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdk.daggerhilt.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}