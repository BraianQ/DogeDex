package com.example.dogedex.doglist.domain

class GetDogListUseCase(
    private val dogRepository: DogRepository
) {
    operator fun invoke(): List<Dog> {
        return dogRepository.getDogs()
    }
}
