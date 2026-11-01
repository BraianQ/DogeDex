package com.example.dogedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.auth.domain.GetLoggedInUserUseCase
import com.example.dogedex.navigation.NavDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface MainUiState {
    object Loading : MainUiState
    data class Success(val startDestination: String) : MainUiState
}

class MainViewModel(
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        checkUserSession()
    }

    private fun checkUserSession() {
        viewModelScope.launch {
            val user = getLoggedInUserUseCase()
            val startDestination = if (user != null) {
                NavDestination.DogList.route
            } else {
                NavDestination.Login.route
            }
            _uiState.value = MainUiState.Success(startDestination)
        }
    }
}
