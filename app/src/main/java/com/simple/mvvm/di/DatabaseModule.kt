package com.simple.mvvm.di

import android.content.Context
import androidx.room.Room
import com.simple.mvvm.data.local.ArticleDao
import com.simple.mvvm.data.local.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): ArticleDatabase {
        return Room.databaseBuilder(
            appContext,
            ArticleDatabase::class.java,
            "news_db"
        ).build()
    }

    @Provides
    fun provideArticleDao(database: ArticleDatabase): ArticleDao {
        return database.articleDao()
    }
}