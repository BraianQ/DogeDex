package com.example.dogedex.auth.domain

import java.io.Serializable

data class User(
    val id: Long,
    val email: String,
    val authenticationToken: String
) : Serializable
