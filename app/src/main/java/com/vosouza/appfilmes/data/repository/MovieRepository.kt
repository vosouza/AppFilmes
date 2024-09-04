package com.vosouza.appfilmes.data.repository

import com.vosouza.appfilmes.data.model.PopularMoviesResponse
import retrofit2.http.Query

interface MovieRepository {

    suspend fun getPopularMovies(
        language: String,
        page: Int
    ) : PopularMoviesResponse

}