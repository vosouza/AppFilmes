package com.vosouza.appfilmes.data.repository

import com.vosouza.appfilmes.data.repository.implementation.FakeLoginRepositoryImpl
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LoginRepositoryTest {

    lateinit var loginRepository: LoginRepository

    @Before
    fun setup(){
        loginRepository = FakeLoginRepositoryImpl()
    }

    @Test
    fun `Assert if login logic returns true`() = runTest {

        val response = loginRepository.login("123", "user")
        assertTrue(response)
    }

    @Test
    fun `Assert if login logic returns false`() = runTest {

        val response = loginRepository.login("user", "123")

        assertFalse(response)
    }
}