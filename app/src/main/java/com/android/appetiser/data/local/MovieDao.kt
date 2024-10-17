package com.android.appetiser.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.appetiser.data.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movie: Movie)

    @Query("SELECT * FROM favorite_movies")
    suspend fun getFavoriteMovies(): List<Movie>

    @Delete
    suspend fun deleteFavorite(movie: Movie)
}