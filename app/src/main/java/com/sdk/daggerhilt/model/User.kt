package com.sdk.daggerhilt.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val login: String,
    val avatar_url: String,
    val node_id: String
)