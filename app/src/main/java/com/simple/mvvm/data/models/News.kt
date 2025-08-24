package com.simple.mvvm.data.models

import kotlinx.serialization.Serializable

@Serializable
data class News(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) {
    companion object {
        fun dummies(count: Int) = News(
            status = "status",
            totalResults = 10,
            articles = List(count) {
                Article.dummy(it)
            }
        )
    }
}