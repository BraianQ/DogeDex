package com.example.dogedex.doglist.data.api

import com.example.dogedex.doglist.data.api.dto.AddDogToUserRequest
import com.example.dogedex.doglist.data.api.dto.DefaultApiResponse
import com.example.dogedex.doglist.data.api.dto.DogApiResponse
import com.example.dogedex.doglist.data.api.dto.SingleDogApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

const val BASE_URL = "https://todogs.herokuapp.com/api/v1/"
const val GET_DOG_BY_ML_ID = "find_dog_by_ml_id"

interface DogApiService {
    @GET("dogs")
    suspend fun getAllDogs(): DogApiResponse

    @Headers("$NEEDS_AUTH_HEADER_KEY: true")
    @POST("add_dog_to_user")
    suspend fun addDogToUser(
        @Body addDogToUserRequest: AddDogToUserRequest
    ): DefaultApiResponse

    @Headers("$NEEDS_AUTH_HEADER_KEY: true")
    @GET("get_user_dogs")
    suspend fun getUserDogs(): DogApiResponse

    @GET(GET_DOG_BY_ML_ID)
    suspend fun getDogByMlId(@Query("ml_id") mlId: String): SingleDogApiResponse
}
