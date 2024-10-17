package com.android.appetiser.data.repository

import com.android.appetiser.data.local.MovieDao
import com.android.appetiser.data.model.Movie
import com.android.appetiser.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao
) {
    suspend fun searchMovies(term: String): List<Movie> {
        return apiService.searchMovies(term).movies
    }

    suspend fun addFavorite(movie: Movie) {
        movieDao.insertFavorite(movie)
    }

    suspend fun getFavorites(): List<Movie> {
        return movieDao.getFavoriteMovies()
    }

    suspend fun removeFavorite(movie: Movie) {
        movieDao.deleteFavorite(movie)
    }
}