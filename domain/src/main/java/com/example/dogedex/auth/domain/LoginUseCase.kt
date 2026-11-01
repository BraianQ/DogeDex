package com.example.dogedex.auth.domain

import com.example.dogedex.doglist.domain.ApiResponse

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): ApiResponse<User> {
        return authRepository.login(email, password)
    }
}
