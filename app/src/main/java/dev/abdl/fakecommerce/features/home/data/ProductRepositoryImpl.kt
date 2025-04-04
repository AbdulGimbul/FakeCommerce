package dev.abdl.fakecommerce.features.home.data

import dev.abdl.fakecommerce.features.home.domain.ProductApiModelItem
import dev.abdl.fakecommerce.network.NetworkException
import dev.abdl.fakecommerce.network.NetworkResult
import dev.abdl.fakecommerce.network.RequestHandler
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val requestHandler: RequestHandler
) : ProductRepository {
    override suspend fun getProducts(): NetworkResult<List<ProductApiModelItem>, NetworkException> {
        return requestHandler.get(
            listOf("products")
        )
    }

    override suspend fun getProductDetail(id: Int): NetworkResult<ProductApiModelItem, NetworkException> {
        return requestHandler.get(
            listOf("products", id.toString())
        )
    }
}
