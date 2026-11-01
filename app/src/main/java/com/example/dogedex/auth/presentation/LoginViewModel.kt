package com.example.dogedex.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.auth.domain.LoginUseCase
import com.example.dogedex.doglist.domain.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("Por favor, completa todos los campos")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            when (val response = loginUseCase(email, password)) {
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
