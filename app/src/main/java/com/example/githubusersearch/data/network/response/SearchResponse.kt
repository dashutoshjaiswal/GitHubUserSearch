package com.example.githubusersearch.data.network.response

import com.example.githubusersearch.data.database.model.User
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    val items: List<User>
)