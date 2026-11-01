package com.example.dogedex.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.auth.domain.SignUpUseCase
import com.example.dogedex.doglist.domain.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun signUp(email: String, password: String, passwordConfirmation: String) {
        if (email.isBlank() || password.isBlank() || passwordConfirmation.isBlank()) {
            _uiState.value = AuthUiState.Error("Por favor, completa todos los campos")
            return
        }

        if (password != passwordConfirmation) {
            _uiState.value = AuthUiState.Error("Las contraseñas no coinciden")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            when (val response = signUpUseCase(email, password, passwordConfirmation)) {
                is ApiResponse.Success -> {
                    _uiState.value = AuthUiState.Success(response.data)
                }
                is ApiResponse.Error -> {
                    _uiState.value = AuthUiState.Error(response.message)
                }
            }
        }
    }

    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }
}
