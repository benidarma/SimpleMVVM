package com.simple.mvvm.data.network

import com.simple.mvvm.data.models.Movie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getMovies(): ApiResult<List<Movie>> = try {
        val response = apiService.getMovies()
        ApiResult.Success(response)
    } catch (_: Exception) {
        ApiResult.Failure(ApiError(11, "GET_MOVIES"))
    }

    suspend fun createMovie(movie: Movie): ApiResult<Movie> = try {
        val response = apiService.createMovie(movie)
        if (response.isSuccessful) {
            ApiResult.Success(response.body()!!)
        } else {
            ApiResult.Failure(ApiError(22, "CREATE_MOVIE"))
        }
    } catch (_: Exception) {
        ApiResult.Failure(ApiError(99, "CREATE_MOVIE"))
    }
}