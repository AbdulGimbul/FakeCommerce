package dev.abdl.fakecommerce.features.home.data

import dev.abdl.fakecommerce.features.home.domain.ProductApiModelItem
import dev.abdl.fakecommerce.network.NetworkException
import dev.abdl.fakecommerce.network.NetworkResult

interface ProductRepository {
    suspend fun getProducts(): NetworkResult<List<ProductApiModelItem>, NetworkException>
    suspend fun getProductDetail(id: Int): NetworkResult<ProductApiModelItem, NetworkException>
}
