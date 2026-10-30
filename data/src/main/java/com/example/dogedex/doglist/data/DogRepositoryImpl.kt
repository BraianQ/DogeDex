package com.example.dogedex.doglist.data

import com.example.dogedex.doglist.data.api.DogApiService
import com.example.dogedex.doglist.data.api.dto.toDomainList
import com.example.dogedex.doglist.data.api.makeNetworkCall
import com.example.dogedex.doglist.domain.ApiResponse
import com.example.dogedex.doglist.domain.Dog
import com.example.dogedex.doglist.domain.DogRepository

class DogRepositoryImpl(
    private val apiService: DogApiService
) : DogRepository {

    override suspend fun getDogs(): ApiResponse<List<Dog>> = makeNetworkCall {
        val response = apiService.getAllDogs()
        if (response.isSuccess) {
            response.data.dogs.toDomainList()
        } else {
            throw Exception(response.message)
        }
    }
}
