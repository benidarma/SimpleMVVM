package com.simple.mvvm.di

import android.content.Context
import androidx.room.Room
import com.simple.mvvm.data.local.MovieDao
import com.simple.mvvm.data.local.LocalDatabase
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
    ): LocalDatabase {
        return Room.databaseBuilder(
            appContext,
            LocalDatabase::class.java,
            "movie_db"
        ).build()
    }

    @Provides
    fun provideMovieDao(database: LocalDatabase): MovieDao {
        return database.movieDao()
    }
}