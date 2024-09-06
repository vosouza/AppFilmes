package com.vosouza.appfilmes.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vosouza.appfilmes.core.util.ResultStatus
import com.vosouza.appfilmes.data.model.MovieDetailResponse
import com.vosouza.appfilmes.data.repository.MovieRepository
import com.vosouza.appfilmes.ui.details.viewmodel.DetailsState
import com.vosouza.appfilmes.ui.details.viewmodel.DetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()
    private val movieRepository = mockk<MovieRepository>(relaxed = true)
    private val viewmodel by lazy {
        DetailsViewModel(
            movieRepository,
            dispatcher
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `Assert if getMovie return success`() {
        val mockMovie = mockMovie()
        val response = DetailsState(mockMovie)
        val expected = ResultStatus.Success(response)

        coEvery { movieRepository.getMovieDetail(any()) } returns mockMovie

        runTest {
            viewmodel.getMovie("123")
        }

        val actualResult = viewmodel.detailState.value

        assertEquals(actualResult, expected)
    }

    @Test
    fun `Assert if getMovie return error`() {
        val response = Exception()

        coEvery { movieRepository.getMovieDetail(any()) } throws response

        runTest {
            viewmodel.getMovie("123")
        }

        val actualResult = viewmodel.detailState.value

        assertThat(actualResult, instanceOf(ResultStatus.Error::class.java))
    }

    @Test
    fun `Assert that getMovie return error when provided no numerical id`() {
        val mockMovie = mockMovie()

        coEvery { movieRepository.getMovieDetail(any()) } returns mockMovie

        runTest {
            viewmodel.getMovie("12c3")
        }

        val actualResult = viewmodel.detailState.value

        assertThat(actualResult, instanceOf(ResultStatus.Error::class.java))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    private fun mockMovie() = MovieDetailResponse()
}