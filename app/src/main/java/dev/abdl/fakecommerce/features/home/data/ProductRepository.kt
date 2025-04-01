package dev.abdl.fakecommerce.features.home.data

import dev.abdl.fakecommerce.features.home.domain.ProductApiModel
import dev.abdl.fakecommerce.network.NetworkException
import dev.abdl.fakecommerce.network.NetworkResult

interface ProductRepository {
    suspend fun getProducts(): NetworkResult<ProductApiModel, NetworkException>
}