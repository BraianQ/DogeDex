package com.example.dogedex.auth.presentation

import com.example.dogedex.auth.domain.User

sealed interface AuthUiState {
    object Idle : AuthUiState
    object Loading : AuthUiState
    data class Success(val user: User) : AuthUiState
    data class Error(val message: String) : AuthUiState
}
