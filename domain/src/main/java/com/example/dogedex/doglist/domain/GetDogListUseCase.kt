package com.example.dogedex.doglist.domain

class GetDogListUseCase(
    private val dogRepository: DogRepository
) {
    suspend operator fun invoke(): List<Dog> {
        return dogRepository.getDogs()
    }
}
