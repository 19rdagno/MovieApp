package com.android.appetiser.movie.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.android.appetiser.data.model.Movie
import com.android.appetiser.movie.viewmodel.MovieViewModel

@Composable
fun MovieDetailScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    movie: Movie
) {
    val isFavorite = remember { mutableStateOf(false) }

    LaunchedEffect(movie.trackId) {
        viewModel.isFavorite(movie.trackId) { result ->
            isFavorite.value = result
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            AsyncImage(
                model = movie.artworkUrl100,
                contentDescription = movie.trackName,
                modifier = Modifier
                    .fillMaxSize()
            )

            IconButton(onClick = {
                if (isFavorite.value) {
                    viewModel.removeFavorite(movie)
                    isFavorite.value = false
                } else {
                    viewModel.addFavorite(movie)
                    isFavorite.value = true
                }
            }, modifier = Modifier.align(Alignment.Center)) {
                Icon(
                    imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color.Red,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        movie.trackName?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        movie.trackPrice?.let {
            Text(
                text = "$$it", // Format price as needed
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        movie.primaryGenreName?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Text(
            text = movie.longDescription ?: "No description available.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp),
            color = Color.Gray
        )
    }
}