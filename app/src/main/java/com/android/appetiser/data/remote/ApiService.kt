package com.android.appetiser.data.remote

import com.android.appetiser.data.model.Movie
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun searchMovies(
        @Query("term") term: String = "star",
        @Query("country") country: String = "au",
        @Query("media") media: String = "movie"
    ): MovieResponse
}

data class MovieResponse(
    @SerializedName("results")
    val movies: List<Movie>
)