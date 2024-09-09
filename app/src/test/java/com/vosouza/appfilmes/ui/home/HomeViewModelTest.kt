package com.vosouza.appfilmes.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vosouza.appfilmes.core.util.ResultStatus
import com.vosouza.appfilmes.data.model.MovieResponse
import com.vosouza.appfilmes.data.model.PopularMoviesResponse
import com.vosouza.appfilmes.data.repository.MovieDatabaseRepository
import com.vosouza.appfilmes.data.repository.MovieRepository
import com.vosouza.appfilmes.ui.home.state.HomeTabs
import com.vosouza.appfilmes.ui.home.viewmodel.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest{

    @get:Rule
    var mainCoroutineRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()
    private val databaseRepository = mockk<MovieDatabaseRepository>(relaxed = true)
    private val movieRepository = mockk<MovieRepository>(relaxed = true)
    private val viewmodel by lazy {
        HomeViewModel(
            movieRepository,
            databaseRepository,
            dispatcher
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `Assert if getAllMovies return success`() {
        val mock = mockResponse(0)

        coEvery { movieRepository.getPopularMovies(any(), any()) } returns mock

        runTest {
            viewmodel.getAllMovies(1)
        }

        val response = viewmodel.homeState.value
        assertThat(response.moviesResponse, instanceOf(ResultStatus.Success::class.java))
    }

    @Test
    fun `Assert if getAllMovies return error by exception`() {
        val mock = Exception()

        coEvery { movieRepository.getPopularMovies(any(), any()) } throws mock

        runTest {
            viewmodel.getAllMovies(1)
        }

        val response = viewmodel.homeState.value
        assertThat(response.moviesResponse, instanceOf(ResultStatus.Error::class.java))
    }

    @Test
    fun `Assert that selectTab updates model`() {

        runTest {
            viewmodel.selectTab(HomeTabs.ALL_MOVIES)
        }

        val response = viewmodel.homeState.value

        assertEquals(response.selectedTab, HomeTabs.ALL_MOVIES)
    }

    @Test
    fun `Assert that getMoreMovie adds movies to list`() {
        val mock = mockResponse(2)

        coEvery { movieRepository.getPopularMovies(any(), any()) } returns mock

        runTest {
            viewmodel.getAllMovies(1)
        }
        runTest {
            viewmodel.getMoreMovies(1)
        }

        val response = viewmodel.homeState.value.movieList.size

        assertEquals(response, 4)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    private fun mockResponse(listsize: Int) = PopularMoviesResponse(
        results = returnList(listsize),
        totalPages = 100L,
    )
    private fun returnList(listsize: Int) = mutableListOf<MovieResponse>().also { list ->
        repeat(listsize) {
            list.add(MovieResponse())
        }
    }
}