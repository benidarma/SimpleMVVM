package com.simple.mvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.simple.mvvm.data.models.Movie

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}