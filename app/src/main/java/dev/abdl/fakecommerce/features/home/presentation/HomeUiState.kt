package dev.abdl.fakecommerce.features.home.presentation

import dev.abdl.fakecommerce.features.home.domain.Product

data class HomeUiState(
    val products: List<Product> = emptyList(),
    val selectedCategory: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
