package com.example.githubusersearch.data.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    indices = [Index("id")]
)
data class User(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val userName: String,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("site_admin")
    val isAdmin: Boolean,
    @SerializedName("score")
    val score: Int,
    @SerializedName("type")
    val type: String
)
