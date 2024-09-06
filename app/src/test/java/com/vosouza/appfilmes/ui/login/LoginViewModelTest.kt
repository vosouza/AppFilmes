package com.vosouza.appfilmes.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vosouza.appfilmes.core.util.ResultStatus
import com.vosouza.appfilmes.data.repository.LoginRepository
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
class LoginViewModelTest {

    @get:Rule
    var mainCoroutineRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()
    private val loginRepository = mockk<LoginRepository>(relaxed = true)
    private val viewmodel by lazy {
        LoginViewModel(
            loginRepository, dispatcher
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `Assert if enableLoginButton enables button`() {

        runTest {
            viewmodel.setUser("asd")
            viewmodel.setPassword("123")
        }

        val actualResult = viewmodel.loginState.value

        assertTrue(actualResult.loginEnable)
    }

    @Test
    fun `Assert if enableLoginButton disables button`() {

        runTest {
            viewmodel.setUser("asd")
            viewmodel.setPassword("")
        }

        val actualResult = viewmodel.loginState.value

        assertFalse(actualResult.loginEnable)
    }

    @Test
    fun `Assert if enableLoginButton disables button after change on password`() {

        runTest {
            viewmodel.setUser("asd")
            viewmodel.setPassword("123")
            viewmodel.setPassword("")
        }

        val actualResult = viewmodel.loginState.value

        assertFalse(actualResult.loginEnable)
    }

    @Test
    fun `Assert if enableLoginButton disables button after change on user`() {

        runTest {
            viewmodel.setUser("asd")
            viewmodel.setPassword("123")
            viewmodel.setUser("")
        }

        val actualResult = viewmodel.loginState.value

        assertFalse(actualResult.loginEnable)
    }


    @Test
    fun `Assert success login`() {

        coEvery { loginRepository.login(any(), any()) } returns true

        runTest {
            viewmodel.setUser("user")
            viewmodel.setPassword("123")
            viewmodel.verifyLogin()
        }

        val actualResult = viewmodel.loginState.value.loginStatus

        assertThat(actualResult, instanceOf(ResultStatus.Success::class.java))
    }

    @Test
    fun `Assert error login`() {

        coEvery { loginRepository.login(any(), any()) } returns false

        runTest {
            viewmodel.setUser("user")
            viewmodel.setPassword("122d3")
            viewmodel.verifyLogin()
        }

        val actualResult = viewmodel.loginState.value.loginStatus

        assertThat(actualResult, instanceOf(ResultStatus.Error::class.java))
    }

    @Test
    fun `Assert error login by exception`() {

        coEvery { loginRepository.login(any(), any()) } throws Exception()

        runTest {
            viewmodel.setUser("user")
            viewmodel.setPassword("122d3")
            viewmodel.verifyLogin()
        }

        val actualResult = viewmodel.loginState.value.loginStatus

        assertThat(actualResult, instanceOf(ResultStatus.Error::class.java))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}