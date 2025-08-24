package com.simple.mvvm.data.network

import com.simple.mvvm.data.models.Movie
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("movies")
    suspend fun getMovies(): List<Movie>

    @POST("movies")
    suspend fun createMovie(@Body movie: Movie): Response<Movie>
}
