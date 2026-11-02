package com.example.dogedex.doglist.domain

class AddDogToUserUseCase(
    private val dogRepository: DogRepository
) {
    suspend operator fun invoke(dogId: Long): ApiResponse<Unit> = dogRepository.addDogToUser(dogId)
}
