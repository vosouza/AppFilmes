package com.vosouza.appfilmes.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vosouza.appfilmes.core.util.ResultStatus
import com.vosouza.appfilmes.data.di.IoDispatcher
import com.vosouza.appfilmes.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: LoginRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.stateIn(
        viewModelScope, SharingStarted.Eagerly, _loginState.value
    )

    fun setUser(user: String) {
        _loginState.value = _loginState.value.copy(
            user = user
        )
        enableLoginButton()
    }

    fun setPassword(password: String) {
        _loginState.value = _loginState.value.copy(
            password = password
        )
        enableLoginButton()
    }

    private fun enableLoginButton() {
        val stateValue = _loginState.value
        _loginState.value = if (stateValue.user.isNotEmpty() && stateValue.password.isNotEmpty()) {
            _loginState.value.copy(
                loginEnable = true
            )
        } else {
            _loginState.value.copy(
                loginEnable = false
            )
        }
    }

    fun verifyLogin() {
        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(
                loginStatus = ResultStatus.Loading
            )

            val stateValue = _loginState.value.copy()
            try {
                val result = withContext(dispatcher) {
                    repository.login(stateValue.password, stateValue.user)
                }

                _loginState.value = _loginState.value.copy(
                    loginStatus = if (result) ResultStatus.Success(Unit) else ResultStatus.Error(
                        Exception()
                    )
                )
            } catch (e: Exception) {
                _loginState.value = _loginState.value.copy(
                    loginStatus = ResultStatus.Error(e)
                )
            }
        }
    }
}

data class LoginState(
    var user: String = "",
    var password: String = "",
    var loginEnable: Boolean = false,
    val loginStatus: ResultStatus<Unit>? = null,
)