package com.example.dogedex.auth.data.api

import com.example.dogedex.auth.data.request.LoginRequest
import com.example.dogedex.auth.data.request.SignUpRequest
import com.example.dogedex.auth.data.response.UserApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("sign_in")
    suspend fun login(@Body loginRequest: LoginRequest): UserApiResponse

    @POST("sign_up")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): UserApiResponse
}
