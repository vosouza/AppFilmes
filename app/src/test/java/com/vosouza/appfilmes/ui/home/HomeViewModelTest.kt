package com.vosouza.appfilmes.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vosouza.appfilmes.core.util.ResultStatus
import com.vosouza.appfilmes.data.repository.LoginRepository
import com.vosouza.appfilmes.data.repository.MovieRepository
import com.vosouza.appfilmes.ui.home.viewmodel.HomeViewModel
import com.vosouza.appfilmes.ui.login.viewmodel.LoginState
import com.vosouza.appfilmes.ui.login.viewmodel.LoginViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
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

@ExperimentalCoroutinesApi
class HomeViewModelTest{

    @get:Rule
    var mainCoroutineRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()
    private val loginRepository = mockk<MovieRepository>(relaxed = true)
    private val viewmodel by lazy {
        HomeViewModel(
            loginRepository,
            dispatcher
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `Assert if enableLoginButton enables button`() {

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

}