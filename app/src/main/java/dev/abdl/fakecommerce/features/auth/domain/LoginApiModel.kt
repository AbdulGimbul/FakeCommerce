package dev.abdl.fakecommerce.features.auth.domain

import kotlinx.serialization.Serializable

@Serializable
data class LoginApiModel(
    val token: String? = null,
)