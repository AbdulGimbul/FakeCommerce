package dev.abdl.fakecommerce.features.detail.presentation

import dev.abdl.fakecommerce.features.home.domain.ProductApiModelItem

data class DetailUiState(
    val product: ProductApiModelItem? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isAddedToCart: Boolean = false,
    val isShowSnackbar: Boolean = false
)
