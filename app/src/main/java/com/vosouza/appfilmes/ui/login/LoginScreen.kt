package com.vosouza.appfilmes.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vosouza.appfilmes.R
import com.vosouza.appfilmes.core.util.ResultStatus
import com.vosouza.appfilmes.ui.login.viewmodel.LoginViewModel
import com.vosouza.appfilmes.ui.theme.black
import com.vosouza.appfilmes.ui.theme.buttonDisable
import com.vosouza.appfilmes.ui.theme.gray
import com.vosouza.appfilmes.ui.theme.orange

@Composable
fun LoginScreen(
    modifier: Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
) {
    val state by viewModel.loginState.collectAsStateWithLifecycle()
    Scaffold { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(black)
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Image(
                painter = painterResource(id = R.drawable.splash),
                contentDescription = stringResource(R.string.logo),
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .size(224.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            TextField(
                value = state.user,
                onValueChange = { viewModel.setUser(it) },
                label = { Text(stringResource(R.string.user)) },
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.user_icon),
                        contentDescription = stringResource(R.string.user)
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.cancel_icon),
                        contentDescription = stringResource(R.string.user)
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = gray,
                    unfocusedTextColor = gray
                ),
                singleLine = true,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )

            Spacer(modifier = Modifier.height(48.dp))

            TextField(
                value = state.password,
                onValueChange = { viewModel.setPassword(it) },
                singleLine = true,
                label = { Text(stringResource(R.string.password)) },
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.password_icon),
                        contentDescription = stringResource(R.string.password)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { viewModel.setPassword("") }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.cancel_icon),
                            contentDescription = stringResource(id = R.string.cancel)
                        )
                    }
                },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = gray,
                    unfocusedTextColor = gray
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { viewModel.verifyLogin() },
                enabled = state.loginEnable,
                colors = ButtonColors(
                    contentColor = Color.White,
                    containerColor = orange,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = buttonDisable
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    modifier = modifier.padding(top = 5.dp, bottom = 5.dp),
                    text = stringResource(R.string.loggin),
                    fontSize = 20.sp
                )
            }

            when (state.loginStatus) {
                is ResultStatus.Loading -> CircularProgressIndicator()
                is ResultStatus.Success -> navigateToHome.invoke()
                is ResultStatus.Error -> Text(
                    text = stringResource(R.string.loggin_error),
                    fontSize = 14.sp,
                    color = Color.Red
                )

                else -> {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            TextButton(onClick = { }) {
                Text(stringResource(R.string.forgot_password), color = Color.White)
            }
        }
    }

}


@Composable
@Preview
fun Preview() {
    LoginScreen(Modifier, navigateToHome = { })
}