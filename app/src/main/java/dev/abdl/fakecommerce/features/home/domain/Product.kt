package dev.abdl.fakecommerce.features.home.domain

data class Product(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val priceTag: String,
    val category: String
)
