package com.example.githubusersearch.data.repository

import android.content.Context
import com.example.githubusersearch.data.Status
import com.example.githubusersearch.data.database.AppDatabase
import com.example.githubusersearch.data.database.model.User
import com.example.githubusersearch.data.network.GithubApi
import com.example.githubusersearch.data.network.response.SearchResponse
import com.example.githubusersearch.data.preference.PreferenceProvider
import com.example.githubusersearch.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class SearchRepository(
    private val api: GithubApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider,
    context: Context
) {

    private val appContext = context.applicationContext

    fun getRepos(searchQuery: String): Flow<Status<List<User>>> {

        return object : BaseRepositoryRemoteAndLocal<SearchResponse, List<User>, List<User>>() {

            // make request
            override suspend fun fetchRemote(): Response<SearchResponse> =
                api.searchUser(searchQuery)

            // extract data
            override fun getDataFromResponse(response: Response<SearchResponse>): List<User> =
                response.body()!!.items

            // save data
            override suspend fun saveRemote(data: List<User>) {
                prefs.setSearchQuery(searchQuery)
                db.userDao().delete()
                db.userDao().insertUsers(data)
            }

            // return saved data
            override fun fetchLocal(): Flow<List<User>> = db.userDao().getAllUSer()

            // should fetch data from remote api or local db
            override fun shouldFetch(): Boolean = NetworkUtils.getNetwork(appContext)

        }.asFlow().flowOn(Dispatchers.IO)

    }

}