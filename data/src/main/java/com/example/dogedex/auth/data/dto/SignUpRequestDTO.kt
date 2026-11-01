package com.example.dogedex.auth.data.dto

import com.google.gson.annotations.SerializedName

data class SignUpRequestDTO(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("password_confirmation") val passwordConfirmation: String
)
