package com.example.dogedex.doglist.domain

interface DogRepository {
    suspend fun getDogs(): ApiResponse<List<Dog>>
}
