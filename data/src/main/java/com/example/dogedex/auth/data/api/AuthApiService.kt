package com.example.dogedex.auth.data.api

import com.example.dogedex.auth.data.dto.LoginRequestDTO
import com.example.dogedex.auth.data.dto.SignUpRequestDTO
import com.example.dogedex.auth.data.dto.UserApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("sign_in")
    suspend fun login(@Body loginRequestDTO: LoginRequestDTO): UserApiResponse

    @POST("sign_up")
    suspend fun signUp(@Body signUpRequestDTO: SignUpRequestDTO): UserApiResponse
}
