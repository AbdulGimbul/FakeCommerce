package dev.abdl.fakecommerce.features.cart.presentation

import dev.abdl.fakecommerce.features.cart.domain.CartEntity

data class CartUiState(
    val cartItems: List<CartEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showInvoice: Boolean = false
) {
    val totalPrice: Double get() = cartItems.sumOf { it.price * it.quantity }
}