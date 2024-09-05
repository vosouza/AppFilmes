package com.vosouza.appfilmes.data.network

import com.vosouza.appfilmes.data.model.MovieDetailResponse
import com.vosouza.appfilmes.data.model.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    suspend fun getPopularMoviesList(
        @Query("language") language: String,
        @Query("page") page: Int,
    ): PopularMoviesResponse

    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") movieId: Int): MovieDetailResponse

}