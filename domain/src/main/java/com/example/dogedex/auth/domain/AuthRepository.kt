package com.example.dogedex.auth.domain

import com.example.dogedex.doglist.domain.ApiResponse

interface AuthRepository {
    suspend fun login(email: String, password: String): ApiResponse<User>
    suspend fun signUp(email: String, password: String, passwordConfirmation: String): ApiResponse<User>
    suspend fun getLoggedInUser(): User?
    suspend fun logout()
}
