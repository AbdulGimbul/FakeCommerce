package dev.abdl.fakecommerce.features.home.domain


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductApiModelItem(
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String,
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("price")
    val price: Double,
    @SerialName("rating")
    val rating: Rating,
    @SerialName("title")
    val title: String
)

@Serializable
data class Rating(
    @SerialName("count")
    val count: Int,
    @SerialName("rate")
    val rate: Double
)

fun ProductApiModelItem.toProduct(): Product {
    return Product(
        id = this.id,
        imageUrl = this.image,
        title = this.title,
        priceTag = this.price.toString(),
        category = this.category
    )
}
