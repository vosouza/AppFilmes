package com.vosouza.appfilmes.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vosouza.appfilmes.data.exception.GenericException
import com.vosouza.appfilmes.data.exception.Http400Exception
import com.vosouza.appfilmes.data.exception.InternetException
import com.vosouza.appfilmes.data.model.MovieDetailResponse
import com.vosouza.appfilmes.data.model.PopularMoviesResponse
import com.vosouza.appfilmes.data.network.MoviesService
import com.vosouza.appfilmes.data.repository.implementation.MoviesRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.internal.matchers.Null
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class RepositoryTest {

    @get:Rule
    var mainCoroutineRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    private val service = mockk<MoviesService>()
    private lateinit var moviesRepository: MovieRepository


    @Before
    fun setup(){
        Dispatchers.setMain(dispatcher)
        moviesRepository = MoviesRepositoryImpl(service)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun test_repository_success() = runTest {
        val expected = mockResponse()
        val response = Response.success(expected)

        coEvery { service.getPopularMoviesList(any(),any()) } returns response

        val repositoryResponse = withContext(Dispatchers.IO){
            moviesRepository.getPopularMovies("",0)
        }

        assertEquals(repositoryResponse, expected)
    }


    @Test
    fun `Verify case of exception is thrown for http 400`() = runTest{
        val expected = Response.error<PopularMoviesResponse>(400, mockErrorBody())

        coEvery { service.getPopularMoviesList(any(),any()) } returns expected

        val exception = assertThrows(Http400Exception::class.java) {
                kotlinx.coroutines.test.runTest {
                    moviesRepository.getPopularMovies("",0)
                }
        }

        assertNotNull(exception)
    }

    @Test
    fun `Verify case of exception is thrown for other error`() = runTest{
        val expected = Response.error<PopularMoviesResponse>(500, mockErrorBody())

        coEvery { service.getPopularMoviesList(any(),any()) } returns expected

        val exception = assertThrows(GenericException::class.java) {
            kotlinx.coroutines.test.runTest {
                moviesRepository.getPopularMovies("",0)
            }
        }

        assertNotNull(exception)
    }

    @Test
    fun `Verify case of exception is thrown for no internet`() = runTest{

        coEvery { service.getPopularMoviesList(any(),any()) } throws IOException()

        val exception = assertThrows(InternetException::class.java) {
            kotlinx.coroutines.test.runTest {
                moviesRepository.getPopularMovies("",0)
            }
        }

        assertNotNull(exception)
    }



    @Test
    fun `Verify details call success`() = runTest {
        val expected = mockDetails()
        val response = Response.success(expected)

        coEvery { service.getMovieDetail(any()) } returns response

        val repositoryResponse = withContext(Dispatchers.IO){
            moviesRepository.getMovieDetail(0)
        }

        assertEquals(repositoryResponse, expected)
    }


    @Test
    fun `Verify details call case of exception is thrown for http 400`() = runTest{
        val expected = Response.error<MovieDetailResponse>(400, mockErrorBody())

        coEvery { service.getMovieDetail(any()) } returns expected

        val exception = assertThrows(Http400Exception::class.java) {
            kotlinx.coroutines.test.runTest {
                moviesRepository.getMovieDetail(0)
            }
        }

        assertNotNull(exception)
    }

    @Test
    fun `Verify details call case of exception is thrown for other error`() = runTest{
        val expected = Response.error<MovieDetailResponse>(500, mockErrorBody())

        coEvery { service.getMovieDetail(any()) } returns expected

        val exception = assertThrows(GenericException::class.java) {
            kotlinx.coroutines.test.runTest {
                moviesRepository.getMovieDetail(0)
            }
        }

        assertNotNull(exception)
    }

    @Test
    fun `Verify details call case of exception is thrown for no internet`() = runTest{

        coEvery { service.getMovieDetail(any()) } throws IOException()

        val exception = assertThrows(InternetException::class.java) {
            kotlinx.coroutines.test.runTest {
                moviesRepository.getMovieDetail(0)
            }
        }

        assertNotNull(exception)
    }

    private fun mockResponse() = PopularMoviesResponse(
        0,
        listOf(),
        0,
        0L
    )

    private fun mockDetails() = MovieDetailResponse()

    private fun mockErrorBody() = "{}".toResponseBody("application/json".toMediaTypeOrNull())

    class EmptyBody()
}