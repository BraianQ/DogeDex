package com.example.dogedex.auth.data

import com.example.dogedex.auth.data.api.AuthApiService
import com.example.dogedex.auth.data.request.LoginRequest
import com.example.dogedex.auth.data.request.SignUpRequest
import com.example.dogedex.auth.data.response.toDomain
import com.example.dogedex.auth.domain.AuthRepository
import com.example.dogedex.auth.domain.User
import com.example.dogedex.doglist.data.api.makeNetworkCall
import com.example.dogedex.doglist.domain.ApiResponse

class AuthRepositoryImpl(
    private val authApiService: AuthApiService
) : AuthRepository {

    override suspend fun login(email: String, password: String): ApiResponse<User> = makeNetworkCall {
        val response = authApiService.login(LoginRequest(email, password))
        if (response.isSuccess) {
            response.data.user.toDomain()
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponse<User> = makeNetworkCall {
        val response = authApiService.signUp(SignUpRequest(email, password, passwordConfirmation))
        if (response.isSuccess) {
            response.data.user.toDomain()
        } else {
            throw Exception(response.message)
        }
    }
}
