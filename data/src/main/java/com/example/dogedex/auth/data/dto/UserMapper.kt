package com.example.dogedex.auth.data.dto

import com.example.dogedex.auth.domain.User

fun UserDTO.toDomain(): User {
    return User(
        id = id,
        email = email,
        authenticationToken = authenticationToken
    )
}
