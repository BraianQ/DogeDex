package com.example.dogedex.doglist.data

import com.example.dogedex.doglist.data.api.DogApiService
import com.example.dogedex.doglist.data.api.dto.AddDogToUserRequest
import com.example.dogedex.doglist.data.api.dto.toDomainList
import com.example.dogedex.doglist.data.api.makeNetworkCall
import com.example.dogedex.doglist.domain.ApiResponse
import com.example.dogedex.doglist.domain.Dog
import com.example.dogedex.doglist.domain.DogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

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

    override suspend fun addDogToUser(dogId: Long): ApiResponse<Unit> = makeNetworkCall {
        val response = apiService.addDogToUser(AddDogToUserRequest(dogId))
        if (response.isSuccess) {
            Unit
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun getUserDogs(): ApiResponse<List<Dog>> = makeNetworkCall {
        val response = apiService.getUserDogs()
        if (response.isSuccess) {
            response.data.dogs.toDomainList()
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun getDogCollection(): ApiResponse<List<Dog>> = withContext(Dispatchers.IO) {
        val allDogsDeferred = async { getDogs() }
        val userDogsDeferred = async { getUserDogs() }

        val allDogsResponse = allDogsDeferred.await()
        val userDogsResponse = userDogsDeferred.await()

        when {
            allDogsResponse is ApiResponse.Success && userDogsResponse is ApiResponse.Success -> {
                val allDogs = allDogsResponse.data
                val userDogIds = userDogsResponse.data.map { it.id }.toSet()

                val collection = allDogs.map { dog ->
                    if (userDogIds.contains(dog.id)) {
                        dog
                    } else {
                        Dog(
                            id = dog.id,
                            index = dog.index,
                            name = "",
                            type = "",
                            heightFemale = 0.0,
                            heightMale = 0.0,
                            imageUrl = "",
                            lifeExpectancy = "",
                            temperament = "",
                            weightFemale = 0.0,
                            weightMale = 0.0
                        )
                    }
                }.sorted()

                ApiResponse.Success(collection)
            }
            allDogsResponse is ApiResponse.Error -> ApiResponse.Error(allDogsResponse.message)
            userDogsResponse is ApiResponse.Error -> ApiResponse.Error(userDogsResponse.message)
            else -> ApiResponse.Error("Error desconocido al obtener la colección de perros")
        }
    }
}
