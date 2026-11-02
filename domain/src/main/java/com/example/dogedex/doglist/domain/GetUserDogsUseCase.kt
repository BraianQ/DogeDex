package com.example.dogedex.doglist.domain

class GetUserDogsUseCase(
    private val dogRepository: DogRepository
) {
    suspend operator fun invoke(): ApiResponse<List<Dog>> = dogRepository.getUserDogs()
}
