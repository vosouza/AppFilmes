package com.vosouza.appfilmes.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vosouza.appfilmes.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: LoginRepository,
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _loginState.value
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
        val stateValue = _loginState.value
        if (repository.login(stateValue.password.toInt(), stateValue.user)) {
            _loginState.value = _loginState.value.copy(
                loginSuccess = true
            )
        }
    }
}

data class LoginState(
    var user: String = "",
    var password: String = "",
    var loginEnable: Boolean = false,
    var loginSuccess: Boolean = false,
)