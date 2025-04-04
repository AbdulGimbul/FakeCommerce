package dev.abdl.fakecommerce.features.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.abdl.fakecommerce.features.cart.data.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        loadCartItems()
    }

    fun onEvent(event: CartUiEvent) {
        when (event) {
            is CartUiEvent.IncreaseQuantity -> updateQuantity(event.productId, 1)
            is CartUiEvent.DecreaseQuantity -> updateQuantity(event.productId, -1)
            CartUiEvent.ClearCart -> clearCart()
            CartUiEvent.Checkout -> checkout()
            is CartUiEvent.DismissInvoice -> {
                _uiState.update { it.copy(showInvoice = false) }
            }
        }
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            cartRepository.getCartItems().collect { items ->
                _uiState.value = _uiState.value.copy(
                    cartItems = items,
                )
            }
        }
    }

    private fun updateQuantity(productId: Int, delta: Int) {
        viewModelScope.launch {
            val updatedItems = _uiState.value.cartItems.mapNotNull { item ->
                if (item.productId == productId) {
                    val newQuantity = item.quantity + delta
                    if (newQuantity > 0) {
                        item.copy(quantity = newQuantity)
                    } else {
                        null
                    }
                } else {
                    item
                }
            }

            _uiState.value = _uiState.value.copy(cartItems = updatedItems)
        }
    }

    private fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
            _uiState.value = CartUiState()
        }
    }

    private fun checkout() {
        _uiState.update { it.copy(showInvoice = true) }
    }
}
