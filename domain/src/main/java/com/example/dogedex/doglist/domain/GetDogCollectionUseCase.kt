package com.example.dogedex.doglist.domain

class GetDogCollectionUseCase(
    private val dogRepository: DogRepository
) {
    suspend operator fun invoke(): ApiResponse<List<Dog>> = dogRepository.getDogCollection()
}
