package com.simple.mvvm.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

data class ApiError(val code: Int, val apiCode: String?)

sealed class ApiResult<out T> {
    data class Failure(val error: ApiError) : ApiResult<Nothing>()
    data class Success<out T>(val data: T) : ApiResult<T>()
}

fun <T> Flow<ApiResult<T>>.onError(
    action: suspend (ApiError) -> Unit
) = flow {
    collect {
        if (it is ApiResult.Failure) action.invoke(it.error) else emit(it)
    }
}

suspend fun <T> Flow<ApiResult<T>>.onSuccess(
    action: (T) -> Unit
) {
    collectLatest {
        if (it is ApiResult.Success) action.invoke(it.data)
    }
}