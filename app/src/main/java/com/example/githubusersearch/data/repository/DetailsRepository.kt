package com.example.githubusersearch.data.repository

import android.content.Context
import com.example.githubusersearch.data.Status
import com.example.githubusersearch.data.database.model.UserDetails
import com.example.githubusersearch.data.network.GithubApi
import com.example.githubusersearch.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class DetailsRepository(
    private val api: GithubApi,
    context: Context
) {

    private val appContext = context.applicationContext

    fun getRepoById(name: String): Flow<Status<UserDetails>> {

        return object : BaseRepositoryRemote<UserDetails, UserDetails>() {

            // make request
            override suspend fun fetchRemote(): Response<UserDetails> = api.getUserDetails(name)

            // extract data
            override fun getDataFromResponse(response: Response<UserDetails>): UserDetails =
                response.body()!!

            // should fetch data from remote api or local db
            override fun shouldFetch(): Boolean = NetworkUtils.getNetwork(appContext)

        }.asFlow().flowOn(Dispatchers.IO)

    }

}