package com.simple.mvvm.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.simple.mvvm.data.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<Movie>

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun findById(id: Int): Movie?

    @Upsert
    suspend fun save(movie: Movie)

    @Query("DELETE FROM movie WHERE id = :id")
    suspend fun deleteById(id: Int)
}