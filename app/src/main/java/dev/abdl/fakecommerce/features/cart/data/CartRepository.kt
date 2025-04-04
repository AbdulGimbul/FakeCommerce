package dev.abdl.fakecommerce.features.cart.data

import dev.abdl.fakecommerce.features.cart.domain.CartEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartEntity>>
    suspend fun addToCart(item: CartEntity)
    suspend fun updateCartItem(item: CartEntity)
    suspend fun deleteCartItem(productId: Int)
    suspend fun clearCart()
}

