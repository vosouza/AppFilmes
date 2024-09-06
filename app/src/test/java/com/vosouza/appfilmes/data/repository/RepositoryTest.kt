package com.vosouza.appfilmes.data.repository

import com.vosouza.appfilmes.data.model.MovieDetailResponse
import com.vosouza.appfilmes.data.model.PopularMoviesResponse
import com.vosouza.appfilmes.data.network.MoviesService
import com.vosouza.appfilmes.data.repository.implementation.MoviesRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test

class RepositoryTest {

    val service = mockk<MoviesService>()
    lateinit var moviesRepository: MovieRepository


    @Before
    fun setup(){
        moviesRepository = MoviesRepositoryImpl(service)
    }

    @Test
    fun test_repository_success() = runTest {
        val response = mockResponse()

        coEvery { service.getPopularMoviesList(any(),any()) } returns response

        val repositoryResponse = withContext(Dispatchers.IO){
            moviesRepository.getPopularMovies("",0)
        }

        assertEquals(repositoryResponse, response)
    }


    @Test
    fun `Verify case of exception is thrown`() = runTest {
        val exception = Exception()

        coEvery { service.getPopularMoviesList(any(),any()) } throws exception

        var exceptionIsThrown = false
        try {
            withContext(Dispatchers.IO){
                moviesRepository.getPopularMovies("",0)
            }
        }catch (e: Exception){
            exceptionIsThrown = true
        }

        assertTrue(exceptionIsThrown)
    }

    @Test
    fun `Verify details call success`() = runTest {
        val response = mockDetails()

        coEvery { service.getMovieDetail(any()) } returns response

        val repositoryResponse = withContext(Dispatchers.IO){
            moviesRepository.getMovieDetail(0)
        }

        assertEquals(repositoryResponse, response)
    }


    @Test
    fun `Verify case of exception is thrown for details call`() = runTest {
        val exception = Exception()

        coEvery { service.getMovieDetail(any()) } throws exception

        var exceptionIsThrown = false
        try {
            withContext(Dispatchers.IO){
                moviesRepository.getMovieDetail(0)
            }
        }catch (e: Exception){
            exceptionIsThrown = true
        }

        assertTrue(exceptionIsThrown)
    }

    private fun mockResponse() = PopularMoviesResponse(
        0,
        listOf(),
        0,
        0L
    )

    private fun mockDetails() = MovieDetailResponse()
}