package dev.abdl.fakecommerce.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.abdl.fakecommerce.storage.SessionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionHandler: SessionHandler
): ViewModel() {
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    fun getUsername() = viewModelScope.launch { _username.value = sessionHandler.getUsername().first() }

    fun clearSession() = viewModelScope.launch { sessionHandler.clearData() }
}