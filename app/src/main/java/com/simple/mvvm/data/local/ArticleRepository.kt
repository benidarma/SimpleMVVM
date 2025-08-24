package com.simple.mvvm.data.local

import com.simple.mvvm.data.models.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject constructor(
    private val articleDao: ArticleDao
) {
    fun getAll(): Flow<List<Article>> = articleDao.getAll()

    fun getById(id: Int): Flow<Article?> = articleDao.findById(id)

    suspend fun save(article: Article) = articleDao.save(article)

    suspend fun delete(id: Int) = articleDao.deleteById(id)

    fun getPaginated(
        limit: Int, offset: Int
    ): Flow<List<Article>> = articleDao.getPaginated(limit, offset)
}