package com.android.appetiser.movie.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.appetiser.data.model.Movie
import com.android.appetiser.movie.viewmodel.MovieViewModel

@Composable
fun FavoritesScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    onMovieClick: (Movie) -> Unit
) {
    val favoriteMovies by viewModel.favorites.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "My Favorites",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        when {
            favoriteMovies.isEmpty() -> {
                ShowLoadingMessage()
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(favoriteMovies) { movie ->
                        MovieItem(
                            movie = movie,
                            onMovieClick = onMovieClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ShowLoadingMessage() {
    Text(
        text = "You can use heart icon to add your favorite movies.",
        modifier = Modifier.fillMaxSize(),
        textAlign = TextAlign.Center
    )
}