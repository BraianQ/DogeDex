package com.example.dogedex.doglist.data.api.dto

import com.google.gson.annotations.SerializedName

data class DogResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("index") val index: Int,
    @SerializedName("name_es") val name: String?,
    @SerializedName("dog_type") val type: String?,
    @SerializedName("height_female") val heightFemale: String?,
    @SerializedName("height_male") val heightMale: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("life_expectancy") val lifeExpectancy: String?,
    @SerializedName("temperament") val temperament: String?,
    @SerializedName("weight_female") val weightFemale: String?,
    @SerializedName("weight_male") val weightMale: String?
)
