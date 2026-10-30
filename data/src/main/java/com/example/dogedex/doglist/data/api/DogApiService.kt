package com.example.dogedex.doglist.data.api

import com.example.dogedex.doglist.data.api.dto.DogApiResponse
import retrofit2.http.GET

const val BASE_URL = "https://todogs.herokuapp.com/api/v1/"

interface DogApiService {
    @GET("dogs")
    suspend fun getAllDogs(): DogApiResponse
}
