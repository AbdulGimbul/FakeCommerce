package dev.abdl.fakecommerce.features.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.abdl.fakecommerce.features.auth.data.AuthRepository
import dev.abdl.fakecommerce.features.auth.domain.LoginRequest
import dev.abdl.fakecommerce.network.onError
import dev.abdl.fakecommerce.network.onSuccess
import dev.abdl.fakecommerce.storage.SessionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionHandler: SessionHandler
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.NotAuthenticated())
    val uiState: StateFlow<LoginUiState> = _uiState

    init {
        viewModelScope.launch {
            if (sessionHandler.getToken().first().isNotEmpty()) {
                _uiState.value = LoginUiState.Authenticated
            }
        }
    }

    fun onEvent(uiEvent: LoginUiEvent) {
        when (uiEvent) {
            is LoginUiEvent.EmailChanged -> updateState { it.copy(username = uiEvent.email) }

            is LoginUiEvent.PasswordChanged -> updateState { it.copy(password = uiEvent.password) }

            is LoginUiEvent.Login -> {
                login()
            }

            else -> Unit
        }
    }

    private fun login() {
        val ui = (_uiState.value as? LoginUiState.NotAuthenticated) ?: return
        updateState {
            it.copy(isLoading = true, loginError = null)
        }

        viewModelScope.launch {
            val result = authRepository.login(LoginRequest(ui.username, ui.password))

            withContext(Dispatchers.Main) {
                result.onSuccess {
                    if (it.token != null) {
                        sessionHandler.setUserData(username = ui.username, token = it.token)
                        _uiState.value = LoginUiState.Authenticated
                    }
                }.onError { error ->
                    updateState {
                        it.copy(loginError = error.message, isLoading = false)
                    }
                }
            }
        }
    }

    private fun updateState(update: (LoginUiState.NotAuthenticated) -> LoginUiState.NotAuthenticated) {
        _uiState.value =
            (_uiState.value as? LoginUiState.NotAuthenticated)?.let(update) ?: _uiState.value
    }

}