package com.ncms.module.network

import okhttp3.ResponseBody

sealed class OnNCMResponse<out T> {
    data class Success<out T>(val value: T) : OnNCMResponse<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?,
        val errorString: String?,

        ) : OnNCMResponse<Nothing>()

    object Loading : OnNCMResponse<Nothing>()

}