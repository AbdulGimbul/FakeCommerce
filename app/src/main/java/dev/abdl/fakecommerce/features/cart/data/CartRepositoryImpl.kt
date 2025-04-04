package dev.abdl.fakecommerce.features.cart.data

import dev.abdl.fakecommerce.features.cart.domain.CartEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {
    override fun getCartItems(): Flow<List<CartEntity>> {
        return cartDao.getAllCartItems()
    }

    override suspend fun addToCart(item: CartEntity) {
        cartDao.insertCartItem(item)
    }

    override suspend fun updateCartItem(item: CartEntity) {
        cartDao.updateCartItem(item)
    }

    override suspend fun deleteCartItem(productId: Int) {
        cartDao.deleteCartItemByProductId(productId)
    }

    override suspend fun clearCart() {
        cartDao.clearAllCart()
    }
}

