package com.simple.mvvm.data.network

import com.simple.mvvm.data.models.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getNews(
        query: String,
        from: String,
        sortBy: String,
        apiKey: String
    ): Flow<ApiResult<News>> = flow {
        try {
            val response = apiService.getNews(
                query = query,
                from = from,
                sortBy = sortBy,
                apiKey = apiKey
            )
            emit(ApiResult.Success(response))
        } catch (_: Exception) {
            emit(ApiResult.Failure(ApiError(11, "GET_NEWS")))
        }
    }

    fun createNews(news: News): Flow<ApiResult<News?>> = flow {
        try {
            val response = apiService.createNews(news)
            if (response.isSuccessful) {
                emit(ApiResult.Success(response.body()))
            } else {
                emit(ApiResult.Failure(ApiError(22, "POST_NEWS")))
            }
        } catch (_: Exception) {
            emit(ApiResult.Failure(ApiError(99, "POST_NEWS")))
        }
    }
}