package com.vosouza.appfilmes.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vosouza.appfilmes.data.database.dao.MoviesDAO
import com.vosouza.appfilmes.data.exception.Http400Exception
import com.vosouza.appfilmes.data.model.MovieDbModel
import com.vosouza.appfilmes.data.model.PopularMoviesResponse
import com.vosouza.appfilmes.data.network.MoviesService
import com.vosouza.appfilmes.data.repository.implementation.MovieDatabaseRepositoryImpl
import com.vosouza.appfilmes.data.repository.implementation.MoviesRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class DatabaseRepositoryTest {

    @get:Rule
    var mainCoroutineRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    private val dao = mockk<MoviesDAO>()
    private lateinit var moviesRepository: MovieDatabaseRepository

    @Before
    fun setup(){
        Dispatchers.setMain(dispatcher)
        moviesRepository = MovieDatabaseRepositoryImpl(dao)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `Verify success getAllMovies`() = runTest {
        val expected = mockListSize3()

        coEvery { dao.getAllMovies() } returns expected

        val repositoryResponse = withContext(dispatcher){
            moviesRepository.getAllMovies()
        }

        assertEquals(repositoryResponse, expected)
    }


    @Test
    fun `Verify error getAllMovies`() = runTest{
        val expected = Exception()

        coEvery { dao.getAllMovies() } throws expected

        val exception = assertThrows(Exception::class.java) {
            kotlinx.coroutines.test.runTest {
                moviesRepository.getAllMovies()
            }
        }

        assertNotNull(exception)
    }

    @Test
    fun `Verify success saveMovie`() = runTest {
        coEvery { dao.saveMovie(any()) } returns Unit

        val repositoryResponse = withContext(dispatcher){
            moviesRepository.saveMovie(MovieDbModel())
        }

        assertNotNull(repositoryResponse)
    }


    @Test
    fun `Verify error saveMovie`() = runTest{
        val expected = Exception()

        coEvery { dao.saveMovie(any()) } throws expected

        val exception = assertThrows(Exception::class.java) {
            kotlinx.coroutines.test.runTest {
                moviesRepository.saveMovie(MovieDbModel())
            }
        }

        assertNotNull(exception)
    }

    @Test
    fun `Verify success removeId`() = runTest {
        coEvery { dao.removeById(any()) } returns Unit

        val repositoryResponse = withContext(dispatcher){
            moviesRepository.removeId(1L)
        }

        assertNotNull(repositoryResponse)
    }


    @Test
    fun `Verify error removeId`() = runTest{
        val expected = Exception()

        coEvery { dao.removeById(any()) } throws expected

        val exception = assertThrows(Exception::class.java) {
            kotlinx.coroutines.test.runTest {
                moviesRepository.removeId(1L)
            }
        }

        assertNotNull(exception)
    }


    fun mockListSize3() = listOf(MovieDbModel(),MovieDbModel(),MovieDbModel())
}