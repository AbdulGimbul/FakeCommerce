package dev.abdl.fakecommerce.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.abdl.fakecommerce.storage.SessionHandler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionHandler: SessionHandler
): ViewModel() {

    fun getUsername() = viewModelScope.launch { sessionHandler.getUsername().first() }

    fun clearSession() = viewModelScope.launch { sessionHandler.clearData() }
}