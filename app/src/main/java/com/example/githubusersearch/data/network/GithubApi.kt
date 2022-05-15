package com.example.githubusersearch.data.network

import com.example.githubusersearch.data.database.model.UserDetails
import com.example.githubusersearch.data.network.response.SearchResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL: String = "https://api.github.com"


interface GithubApi {

    // Search
    @GET("/search/users")
    suspend fun searchUser(@Query("q") searchQuery: String): Response<SearchResponse>

/*@GET("/search/users")
suspend fun searchUser(@Query("q") searchQuery: String,
                        @Query("per_page") perPage: Int): Response<SearchResponse>*/

/*@GET("/search/users")
suspend fun searchUser(@Query("q") searchQuery: String,
                        @Query("page") pageIndex: Int,
                        @Query("per_page") perPage: Int): Response<SearchResponse>*/


    // User info
    @GET("/users/{username}")
    suspend fun getUserDetails(
        @Path("username") name: String
    ): Response<UserDetails>


    companion object {
        operator fun invoke(): GithubApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubApi::class.java)
        }
    }

}