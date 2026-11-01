package com.example.dogedex.auth.data.response

import com.example.dogedex.auth.domain.User

fun UserResponse.toDomain(): User {
    return User(
        id = id,
        email = email,
        authenticationToken = authenticationToken
    )
}
