package com.example.dogedex.doglist.domain

class GetDogListUseCase(
    private val dogRepository: DogRepository
) {
    suspend operator fun invoke(): ApiResponse<List<Dog>> {
        return dogRepository.getDogs()
    }
}
