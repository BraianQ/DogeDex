package com.example.dogedex.auth.data.response

import com.google.gson.annotations.SerializedName

data class UserApiResponse(
    @SerializedName("message") val message: String,
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("data") val data: UserDataResponse
)

data class UserDataResponse(
    @SerializedName("user") val user: UserResponse
)

data class UserResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("email") val email: String,
    @SerializedName("authentication_token") val authenticationToken: String
)
