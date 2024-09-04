package com.vosouza.appfilmes.data.repository.implementation

import com.vosouza.appfilmes.data.model.PopularMoviesResponse
import com.vosouza.appfilmes.data.network.MoviesService
import com.vosouza.appfilmes.data.repository.MovieRepository
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val service: MoviesService
) : MovieRepository {

    override suspend fun getPopularMovies(language: String, page: Int): PopularMoviesResponse {
        return service.getPopularMoviesList(language, page)
    }
}