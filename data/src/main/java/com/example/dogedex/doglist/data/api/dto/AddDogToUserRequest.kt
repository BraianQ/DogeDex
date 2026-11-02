package com.example.dogedex.doglist.data.api.dto

import com.google.gson.annotations.SerializedName

data class AddDogToUserRequest(
    @SerializedName("dog_id") val dogId: Long
)
