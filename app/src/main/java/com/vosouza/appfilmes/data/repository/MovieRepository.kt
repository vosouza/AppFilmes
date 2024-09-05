package com.vosouza.appfilmes.data.repository

import com.vosouza.appfilmes.data.model.MovieDetailResponse
import com.vosouza.appfilmes.data.model.PopularMoviesResponse

interface MovieRepository {

    suspend fun getPopularMovies(
        language: String,
        page: Int
    ) : PopularMoviesResponse

    suspend fun getMovieDetail(
        movieId: Int
    ): MovieDetailResponse
}