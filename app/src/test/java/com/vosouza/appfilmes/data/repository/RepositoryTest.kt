package com.vosouza.appfilmes.data.repository

import com.vosouza.appfilmes.data.model.PopularMoviesResponse
import com.vosouza.appfilmes.data.network.MoviesService
import com.vosouza.appfilmes.data.repository.implementation.MoviesRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
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

        every { service.getPopularMoviesList("",0) } returns response

        val repositoryResponse = withContext(Dispatchers.IO){
            moviesRepository.getPopularMovies("",0)
        }

        assertEquals(repositoryResponse, response)
    }

    fun mockResponse() = PopularMoviesResponse(
        0,
        listOf(),
        0,
        0L
    )
}