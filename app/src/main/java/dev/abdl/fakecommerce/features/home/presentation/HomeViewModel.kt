package dev.abdl.fakecommerce.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.abdl.fakecommerce.features.home.data.ProductRepository
import dev.abdl.fakecommerce.features.home.domain.toProduct
import dev.abdl.fakecommerce.network.onError
import dev.abdl.fakecommerce.network.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts(){
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            val result = productRepository.getProducts()

            result.onSuccess { data ->
                val  products = data.map { it.toProduct() }
                _uiState.value = _uiState.value.copy(
                    products = products,
                    isLoading = false,
                    errorMessage = null
                )

            }.onError {
                _uiState.value = _uiState.value.copy(errorMessage = it.message, isLoading = false)
            }
        }
    }

//    fun onEvent(uiEvent){
//
//    }
}