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
    private val authApiService: AuthApiService,
    private val userLocalDataSource: UserLocalDataSource
) : AuthRepository {

    override suspend fun login(email: String, password: String): ApiResponse<User> = makeNetworkCall {
        val response = authApiService.login(LoginRequest(email, password))
        if (response.isSuccess) {
            val user = response.data.user.toDomain()
            userLocalDataSource.saveUser(user)
            user
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
            val user = response.data.user.toDomain()
            userLocalDataSource.saveUser(user)
            user
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun getLoggedInUser(): User? {
        return userLocalDataSource.getUser()
    }

    override suspend fun logout() {
        userLocalDataSource.clearUser()
    }
}
