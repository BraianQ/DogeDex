package com.example.dogedex.doglist.domain

interface DogRepository {
    suspend fun getDogs(): List<Dog>
}
