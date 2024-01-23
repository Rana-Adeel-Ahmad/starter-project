package com.ncms.module.repositry

import com.ncms.module.network.OnNCMResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): OnNCMResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                OnNCMResponse.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        OnNCMResponse.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody(),
                            throwable.toString()
                        )
                    }

                    else -> {
                        OnNCMResponse.Failure(
                            false,
                            null,
                            null,
                            throwable.toString()
                        )
                    }
                }
            }
        }
    }
}