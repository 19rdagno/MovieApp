package com.android.appetiser.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.appetiser.data.model.Movie

@Database(entities = [Movie::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}