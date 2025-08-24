package com.simple.mvvm.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @Embedded(prefix = "source_")
    val source: Source,

    val author: String? = null,
    val title: String,
    val description: String? = null,
    val url: String,
    val urlToImage: String? = null,
    val publishedAt: String,
    val content: String? = null
) {
    companion object {
        fun dummy(id: Int = 1) = Article(
            id = id,
            source = Source.dummy(id.toString()),
            author = "author $id",
            title = "title $id",
            description = "description $id",
            url = "url $id",
            urlToImage = "urlToImage $id",
            publishedAt = "publishedAt $id",
            content = "content $id"
        )

        fun dummies(count: Int) = List(count) {
            dummy(it)
        }
    }
}
