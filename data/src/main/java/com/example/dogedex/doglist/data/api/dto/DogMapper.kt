package com.example.dogedex.doglist.data.api.dto

import com.example.dogedex.doglist.domain.Dog

fun DogResponse.toDomain(): Dog {
    return Dog(
        id = id,
        index = index,
        name = name.orEmpty(),
        type = type.orEmpty(),
        heightFemale = heightFemale?.toDoubleOrNull() ?: 0.0,
        heightMale = heightMale?.toDoubleOrNull() ?: 0.0,
        imageUrl = imageUrl.orEmpty(),
        lifeExpectancy = lifeExpectancy.orEmpty(),
        temperament = temperament.orEmpty(),
        weightFemale = weightFemale?.toDoubleOrNull() ?: 0.0,
        weightMale = weightMale?.toDoubleOrNull() ?: 0.0
    )
}

fun List<DogResponse>.toDomainList(): List<Dog> {
    return map { it.toDomain() }
}
