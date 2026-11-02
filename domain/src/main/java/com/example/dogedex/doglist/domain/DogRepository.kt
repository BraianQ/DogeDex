package com.example.dogedex.doglist.domain

interface DogRepository {
    suspend fun getDogs(): ApiResponse<List<Dog>>
    suspend fun addDogToUser(dogId: Long): ApiResponse<Unit>
    suspend fun getUserDogs(): ApiResponse<List<Dog>>
    suspend fun getDogCollection(): ApiResponse<List<Dog>>
}
