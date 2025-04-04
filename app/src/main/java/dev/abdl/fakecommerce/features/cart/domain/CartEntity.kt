package dev.abdl.fakecommerce.features.cart.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey val productId: Int,
    val title: String,
    val imageUrl: String,
    val price: Double,
    val quantity: Int = 1
)
