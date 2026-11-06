package com.example.dogedex.doglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.auth.domain.LogoutUseCase
import com.example.dogedex.doglist.domain.AddDogToUserUseCase
import com.example.dogedex.doglist.domain.ApiResponse
import com.example.dogedex.doglist.domain.Dog
import com.example.dogedex.doglist.domain.GetDogByMlIdUseCase
import com.example.dogedex.doglist.domain.GetDogCollectionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DogListViewModel(
    private val getDogCollectionUseCase: GetDogCollectionUseCase,
    private val addDogToUserUseCase: AddDogToUserUseCase,
    private val getDogByMlIdUseCase: GetDogByMlIdUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DogListUiState>(DogListUiState.Loading)
    val uiState: StateFlow<DogListUiState> = _uiState.asStateFlow()

    init {
        getDogList()
    }

    fun getDogList() {
        viewModelScope.launch {
            _uiState.value = DogListUiState.Loading
            when (val response = getDogCollectionUseCase()) {
                is ApiResponse.Success -> {
                    _uiState.value = DogListUiState.Success(response.data)
                }
                is ApiResponse.Error -> {
                    _uiState.value = DogListUiState.Error(response.message)
                }
            }
        }
    }

    fun getDogByMlId(mlId: String, onDogFound: (Dog) -> Unit) {
        viewModelScope.launch {
            _uiState.value = DogListUiState.Loading
            when (val response = getDogByMlIdUseCase(mlId)) {
                is ApiResponse.Success -> {
                    getDogList()
                    onDogFound(response.data)
                }
                is ApiResponse.Error -> {
                    _uiState.value = DogListUiState.Error(response.message)
                }
            }
        }
    }

    fun addDogToUser(dogId: Long) {
        viewModelScope.launch {
            _uiState.value = DogListUiState.Loading
            when (val response = addDogToUserUseCase(dogId)) {
                is ApiResponse.Success -> {
                    getDogList()
                }
                is ApiResponse.Error -> {
                    _uiState.value = DogListUiState.Error(response.message)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}
