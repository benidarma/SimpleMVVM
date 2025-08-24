package com.simple.mvvm.data.network

import com.simple.mvvm.data.models.News
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): News

    @POST("news")
    suspend fun createNews(@Body news: News): Response<News>
}
