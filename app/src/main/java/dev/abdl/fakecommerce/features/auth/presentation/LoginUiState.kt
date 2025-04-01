package dev.abdl.fakecommerce.features.auth.presentation

sealed class LoginUiState {
    data class NotAuthenticated(
        val username: String = "",
        val usernameError: String? = null,

        val password: String = "",
        val passwordError: String? = null,

        val isLoading: Boolean = false,

        val loginError: String? = null
    ) : LoginUiState()

    data object Authenticated : LoginUiState()
}