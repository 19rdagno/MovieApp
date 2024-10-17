package com.android.appetiser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.appetiser.data.model.Movie
import com.android.appetiser.movie.theme.AppetiserTheme
import com.android.appetiser.movie.ui.FavoritesScreen
import com.android.appetiser.movie.ui.MovieDetailScreen
import com.android.appetiser.movie.ui.MovieListScreen
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder
import java.net.URLEncoder

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppetiserTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MovieApp()
                }
            }
        }
    }
}

@Composable
fun MovieApp() {
    val navController = rememberNavController()
    val gson = Gson()
    NavHost(navController, startDestination = "movieList") {
        composable("movieList") {
            MovieListScreen(navController) { movie ->
                val movieJson = URLEncoder.encode(gson.toJson(movie), "UTF-8")
                navController.navigate("movieDetail/$movieJson")
            }
        }
        composable("movieDetail/{movieJson}") { backStackEntry ->
            val movieJson = backStackEntry.arguments?.getString("movieJson")?.let {
                URLDecoder.decode(it, "UTF-8")
            }
            val movie = movieJson?.let { gson.fromJson(it, Movie::class.java) }
            if (movie != null) {
                MovieDetailScreen(movie = movie)
            } else {
                Text(text = "Movie not found")
            }
        }
        composable("favorites") {
            FavoritesScreen { movie ->
                val movieJson = URLEncoder.encode(gson.toJson(movie), "UTF-8")
                navController.navigate("movieDetail/$movieJson")
            }
        }
    }
}