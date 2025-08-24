package com.simple.mvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.simple.mvvm.data.models.Article

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}