package com.android.appetiser.movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.appetiser.data.model.Movie
import com.android.appetiser.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _favorites = MutableStateFlow<List<Movie>>(emptyList())
    val favorites: StateFlow<List<Movie>> = _favorites

    fun searchMovies(term: String) {
        viewModelScope.launch {
            if (term.isBlank()) {
                _movies.value = repository.searchMovies("star")
            } else {
                _movies.value = repository.searchMovies(term)
            }
        }
    }

    fun addFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.addFavorite(movie)
            loadFavorites()
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.getFavorites()
        }
    }

    fun removeFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.removeFavorite(movie)
            loadFavorites()
        }
    }

    fun isFavorite(movieId: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val favoriteMovies = repository.getFavorites()
            onResult(favoriteMovies.any { it.trackId == movieId })
        }
    }
}