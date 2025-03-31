package dev.abdl.fakecommerce.features.auth.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.NotAuthenticated())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEvent(uiEvent: LoginUiEvent) {
        when (uiEvent) {
            is LoginUiEvent.EmailChanged -> updateState { it.copy(email = uiEvent.email) }

            is LoginUiEvent.PasswordChanged -> updateState { it.copy(password = uiEvent.password) }

            is LoginUiEvent.Login -> {
            }

            else -> Unit
        }
    }

    private fun updateState(update: (LoginUiState.NotAuthenticated) -> LoginUiState.NotAuthenticated) {
        _uiState.value =
            (_uiState.value as? LoginUiState.NotAuthenticated)?.let(update) ?: _uiState.value
    }

}