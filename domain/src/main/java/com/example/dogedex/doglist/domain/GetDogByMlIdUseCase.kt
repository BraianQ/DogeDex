package com.example.dogedex.doglist.domain

class GetDogByMlIdUseCase(
    private val dogRepository: DogRepository
) {
    suspend operator fun invoke(mlId: String): ApiResponse<Dog> = dogRepository.getDogByMlId(mlId)
}
