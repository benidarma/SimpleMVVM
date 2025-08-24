package com.simple.mvvm.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.simple.mvvm.data.models.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    fun getAll(): Flow<List<Article>>

    @Query("SELECT * FROM article WHERE id = :id")
    fun findById(id: Int): Flow<Article?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(article: Article)

    @Query("DELETE FROM article WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM article LIMIT :limit OFFSET :offset")
    fun getPaginated(limit: Int, offset: Int): Flow<List<Article>>
}