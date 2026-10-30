package com.example.dogedex.doglist.data

import com.example.dogedex.doglist.data.api.DogApiService
import com.example.dogedex.doglist.data.api.dto.toDomainList
import com.example.dogedex.doglist.domain.ApiResponse
import com.example.dogedex.doglist.domain.Dog
import com.example.dogedex.doglist.domain.DogRepository

class DogRepositoryImpl(
    private val apiService: DogApiService
) : DogRepository {

    override suspend fun getDogs(): ApiResponse<List<Dog>> {
        return try {
            val response = apiService.getAllDogs()
            if (response.isSuccess) {
                ApiResponse.Success(response.data.dogs.toDomainList())
            } else {
                ApiResponse.Error(response.message)
            }
        } catch (e: Exception) {
            ApiResponse.Error(e.localizedMessage ?: "Error de conexión al cargar la lista de perros")
        }
    }
}
