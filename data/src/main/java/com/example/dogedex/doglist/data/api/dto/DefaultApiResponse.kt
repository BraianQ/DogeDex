package com.example.dogedex.doglist.data.api.dto

import com.google.gson.annotations.SerializedName

data class DefaultApiResponse(
    @SerializedName("message") val message: String,
    @SerializedName("is_success") val isSuccess: Boolean
)
