package dev.abdl.fakecommerce.features.auth.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.abdl.fakecommerce.ui.components.DefaultPreview
import dev.abdl.fakecommerce.ui.components.DefaultTextField
import dev.abdl.fakecommerce.ui.navigation.Screen

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavController
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState.value) {
        is LoginUiState.Authenticated -> {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) {
                    inclusive = true
                }
            }
        }

        is LoginUiState.NotAuthenticated -> {
            Login(
                uiState = state,
                onEvent = { viewModel.onEvent(it) }
            )
        }
    }
}

@Composable
fun Login(
    uiState: LoginUiState.NotAuthenticated,
    onEvent: (LoginUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 42.dp, bottom = 32.dp),
            text = "Silahkan login!",
        )

        DefaultTextField(
            value = uiState.username,
            label = "Email",
            hint = "email@mail.com",
            leadingIcon = Icons.Filled.Email,
            imeAction = ImeAction.Next,
            error = uiState.usernameError,
            onValueChanged = { onEvent(LoginUiEvent.EmailChanged(it)) }
        )

        DefaultTextField(
            value = uiState.password,
            label = "Password",
            hint = "password",
            leadingIcon = Icons.Filled.Lock,
            imeAction = ImeAction.Done,
            isPasswordField = true,
            error = uiState.passwordError,
            onValueChanged = { onEvent(LoginUiEvent.PasswordChanged(it)) }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
            }
            uiState.loginError?.let { error ->
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            onClick = { onEvent.invoke(LoginUiEvent.Login) }
        ) {
            Text("Login")
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "login",
            )
        }
    }
}

@DefaultPreview
@Composable
private fun LoginScreenPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Login(
                uiState = LoginUiState.NotAuthenticated(),
                onEvent = {}
            )
        }
    }
}