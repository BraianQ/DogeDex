package com.example.dogedex.doglist.domain

sealed interface ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>
    data class Error(val message: String) : ApiResponse<Nothing>
}
