package com.example.dogedex.auth.domain

class GetLoggedInUserUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): User? = authRepository.getLoggedInUser()
}
