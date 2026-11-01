package com.example.dogedex.auth.domain

import com.example.dogedex.doglist.domain.ApiResponse

class SignUpUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, passwordConfirmation: String): ApiResponse<User> {
        return authRepository.signUp(email, password, passwordConfirmation)
    }
}
