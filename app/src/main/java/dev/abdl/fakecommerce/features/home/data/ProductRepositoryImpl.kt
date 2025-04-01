package dev.abdl.fakecommerce.features.home.data

import dev.abdl.fakecommerce.features.home.domain.ProductApiModel
import dev.abdl.fakecommerce.network.NetworkException
import dev.abdl.fakecommerce.network.NetworkResult
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor() : ProductRepository {
    override suspend fun getProducts(): NetworkResult<ProductApiModel, NetworkException> {
        TODO("Not yet implemented")
    }
}