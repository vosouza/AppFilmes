package com.vosouza.appfilmes.data.network

import com.vosouza.appfilmes.data.model.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularMoviesService {

    @GET("movie/popular")
    fun getPopularMoviesList(
        @Query("language") language: String,
        @Query("page") page: Int
    ) : PopularMoviesResponse

}