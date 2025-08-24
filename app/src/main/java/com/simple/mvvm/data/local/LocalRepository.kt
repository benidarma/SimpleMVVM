package com.simple.mvvm.data.local

import com.simple.mvvm.data.models.Movie
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val movieDao: MovieDao
) {
    suspend fun getAll() = movieDao.getAll()

    suspend fun getById(id: Int) = movieDao.findById(id)

    suspend fun save(movie: Movie) = movieDao.save(movie)

    suspend fun delete(id: Int) = movieDao.deleteById(id)
}