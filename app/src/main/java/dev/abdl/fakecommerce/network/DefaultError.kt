package dev.abdl.fakecommerce.network

import kotlinx.serialization.Serializable

@Serializable
data class DefaultError(
    val message: String
)