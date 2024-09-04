package com.vosouza.appfilmes.data.network

import com.vosouza.appfilmes.data.model.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    suspend fun getPopularMoviesList(
        @Query("language") language: String,
        @Query("page") page: Int
    ) : PopularMoviesResponse

}