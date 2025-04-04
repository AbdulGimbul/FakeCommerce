package dev.abdl.fakecommerce.features.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.abdl.fakecommerce.features.cart.data.CartRepository
import dev.abdl.fakecommerce.features.cart.domain.CartEntity
import dev.abdl.fakecommerce.features.home.data.ProductRepository
import dev.abdl.fakecommerce.features.home.domain.ProductApiModelItem
import dev.abdl.fakecommerce.network.onError
import dev.abdl.fakecommerce.network.onSuccess
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val cartRepository: CartRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        savedStateHandle.get<String>("productId")?.let { productId ->
            getProductDetail(productId.toInt())
        }
    }

    fun onEvent(event: DetailUiEvent) {
        when (event) {
            is DetailUiEvent.AddToCartClicked -> {
                addToCart()
            }
        }
    }

    private fun getProductDetail(productId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            repository.getProductDetail(productId)
                .onSuccess { product ->
                    _uiState.value = _uiState.value.copy(
                        product = product,
                        isLoading = false
                    )
                }
                .onError { error ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = error.message,
                        isLoading = false
                    )
                }
        }
    }

    private fun addToCart() {
        _uiState.value = _uiState.value.copy(isShowSnackbar = false)
        viewModelScope.launch {
            _uiState.value.product?.let { product ->
                val item = CartEntity(
                    productId = product.id,
                    title = product.title,
                    imageUrl = product.image,
                    price = product.price
                )
                cartRepository.addToCart(item)
                _uiState.value = _uiState.value.copy(isAddedToCart = true, isShowSnackbar = true)
            }
        }
    }
}
