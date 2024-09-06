package com.vosouza.appfilmes.data.repository.implementation

import com.vosouza.appfilmes.data.exception.GenericException
import com.vosouza.appfilmes.data.exception.Http400Exception
import com.vosouza.appfilmes.data.exception.InternetException
import com.vosouza.appfilmes.data.exception.NullBodyException
import com.vosouza.appfilmes.data.model.MovieDetailResponse
import com.vosouza.appfilmes.data.model.PopularMoviesResponse
import com.vosouza.appfilmes.data.network.MoviesService
import com.vosouza.appfilmes.data.repository.MovieRepository
import java.io.IOException
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val service: MoviesService
) : MovieRepository {

    override suspend fun getPopularMovies(language: String, page: Int): PopularMoviesResponse {
        try {
            val response = service.getPopularMoviesList(language, page)
            if(response.isSuccessful){
                return response.body() ?: throw NullBodyException()
            }else{
                when(response.code()){
                    400 -> throw Http400Exception()
                    else -> throw GenericException()
                }
            }
        }catch (e: IOException){
            throw InternetException()
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailResponse {
        try {
            val response = service.getMovieDetail(movieId)
            if(response.isSuccessful){
                return response.body() ?: throw NullBodyException()
            }else{
                when(response.code()){
                    400 -> throw Http400Exception()
                    else -> throw GenericException()
                }
            }
        }catch (e: IOException){
            throw InternetException()
        }catch (e: Exception){
            throw e
        }
    }
}