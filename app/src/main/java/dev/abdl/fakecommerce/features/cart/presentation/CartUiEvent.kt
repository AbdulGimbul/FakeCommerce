package dev.abdl.fakecommerce.features.cart.presentation

sealed class CartUiEvent {
    data class IncreaseQuantity(val productId: Int) : CartUiEvent()
    data class DecreaseQuantity(val productId: Int) : CartUiEvent()
    object ClearCart : CartUiEvent()
    object Checkout : CartUiEvent()
    object DismissInvoice : CartUiEvent()
}