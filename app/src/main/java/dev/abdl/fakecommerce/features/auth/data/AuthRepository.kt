package dev.abdl.fakecommerce.features.auth.data

import dev.abdl.fakecommerce.features.auth.domain.LoginApiModel
import dev.abdl.fakecommerce.features.auth.domain.LoginRequest
import dev.abdl.fakecommerce.network.NetworkException
import dev.abdl.fakecommerce.network.NetworkResult

interface AuthRepository {
    suspend fun login(request: LoginRequest): NetworkResult<LoginApiModel, NetworkException>
}