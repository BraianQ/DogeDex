package com.example.dogedex.doglist.data.api.dto

import com.google.gson.annotations.SerializedName

data class SingleDogApiResponse(
    @SerializedName("message") val message: String,
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("data") val data: SingleDogDataResponse
)

data class SingleDogDataResponse(
    @SerializedName("dog") val dog: DogResponse
)
