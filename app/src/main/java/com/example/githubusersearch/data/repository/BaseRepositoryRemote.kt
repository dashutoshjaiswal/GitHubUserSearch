package com.example.githubusersearch.data.repository

import com.example.githubusersearch.data.Status
import kotlinx.coroutines.flow.flow
import retrofit2.Response

/**
 * Base repository class
 *
 * [RESPONSE] is for network
 * [DATA] is for the type of extracted from [RESPONSE]
 * */
abstract class BaseRepositoryRemote<RESPONSE, DATA> {

    fun asFlow() = flow<Status<DATA>> {

        // Emit loading status
        emit(Status.loading())

        if (shouldFetch()) {
            try {
                // Fetch data from remote api
                val response = fetchRemote()

                // Parse data from response
                val data = getDataFromResponse(response)

                if (response.isSuccessful && data != null) {
                    // Save data to database
                    emit(Status.success(data))
                } else {
                    // Emit error
                    emit(Status.error(response.message()))
                }

            } catch (e: Exception) {
                e.printStackTrace()
                emit(Status.error("Network error!"))
            }
        }
    }

    // Fetches response
    protected abstract suspend fun fetchRemote(): Response<RESPONSE>

    // Extracts data from remote response (ex.: response.body()!! or response.body()!!.items)
    protected abstract fun getDataFromResponse(response: Response<RESPONSE>): DATA

    // Checks network connection
    protected abstract fun shouldFetch(): Boolean

}