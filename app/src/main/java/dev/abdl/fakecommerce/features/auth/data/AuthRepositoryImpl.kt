package dev.abdl.fakecommerce.features.auth.data

import dev.abdl.fakecommerce.features.auth.domain.LoginApiModel
import dev.abdl.fakecommerce.features.auth.domain.LoginRequest
import dev.abdl.fakecommerce.network.NetworkException
import dev.abdl.fakecommerce.network.NetworkResult
import dev.abdl.fakecommerce.network.RequestHandler
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val requestHandler: RequestHandler
) : AuthRepository {
    override suspend fun login(request: LoginRequest): NetworkResult<LoginApiModel, NetworkException> {
        return requestHandler.post(
            urlPathSegments = listOf("auth", "login"),
            body = request
        )
    }
}