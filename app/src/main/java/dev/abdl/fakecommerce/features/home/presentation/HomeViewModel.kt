package dev.abdl.fakecommerce.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.abdl.fakecommerce.features.home.data.ProductRepository
import dev.abdl.fakecommerce.features.home.domain.Product
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
    private var allProducts: List<Product> = emptyList()

    init {
        getProducts()
    }

    private fun getProducts() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            val result = productRepository.getProducts()

            result.onSuccess { data ->
                allProducts = data.map { it.toProduct() }
                updateProductsList()
            }.onError {
                _uiState.value = _uiState.value.copy(errorMessage = it.message, isLoading = false)
            }
        }
    }

    private fun updateProductsList() {
        val selectedCategory = _uiState.value.selectedCategory
        val filteredProducts = if (selectedCategory != null) {
            allProducts.filter { it.category.equals(selectedCategory, ignoreCase = true) }
        } else {
            allProducts
        }
        _uiState.value = _uiState.value.copy(
            products = filteredProducts,
            isLoading = false,
            errorMessage = null
        )
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.CategorySelected -> {
                _uiState.value = _uiState.value.copy(selectedCategory = event.category)
                updateProductsList()
            }
            is HomeUiEvent.ClearCategoryFilter -> {
                _uiState.value = _uiState.value.copy(selectedCategory = null)
                updateProductsList()
            }
            is HomeUiEvent.RefreshProducts -> {
                getProducts()
            }
        }
    }
}
