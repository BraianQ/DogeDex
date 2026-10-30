package com.example.dogedex.doglist.presentation

import com.example.dogedex.doglist.domain.Dog

sealed interface DogListUiState {
    object Loading : DogListUiState
    data class Success(val dogs: List<Dog>) : DogListUiState
    data class Error(val message: String) : DogListUiState
}
