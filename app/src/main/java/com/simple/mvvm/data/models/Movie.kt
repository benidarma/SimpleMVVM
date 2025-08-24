package com.simple.mvvm.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Movie(
    @PrimaryKey val id: Int,
    @ColumnInfo val movieId: Int,
    @ColumnInfo val title: String,
    @ColumnInfo val description: String
) {
    companion object {
        fun dummy(id: Int = 1) = Movie(
            id = id,
            movieId = id.plus(2),
            title = "Ini Title $id",
            description = "Ini description $id"
        )

        fun dummies(count: Int) = List(count) {
            dummy(id = it)
        }
    }
}