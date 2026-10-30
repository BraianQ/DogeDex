package com.example.dogedex.doglist.data.api.dto

import com.google.gson.annotations.SerializedName

data class DogApiResponse(
    @SerializedName("message") val message: String,
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("data") val data: DogListApiResponse
)

data class DogListApiResponse(
    @SerializedName("dogs") val dogs: List<DogResponse>
)
