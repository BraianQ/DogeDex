package com.example.dogedex.doglist.domain

interface DogRepository {
    fun getDogs(): List<Dog>
}
