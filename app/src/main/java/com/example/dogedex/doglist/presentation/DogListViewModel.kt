package com.example.dogedex.doglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.doglist.domain.ApiResponse
import com.example.dogedex.doglist.domain.GetDogListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DogListViewModel(
    private val getDogListUseCase: GetDogListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DogListUiState>(DogListUiState.Loading)
    val uiState: StateFlow<DogListUiState> = _uiState.asStateFlow()

    init {
        getDogList()
    }

    fun getDogList() {
        viewModelScope.launch {
            _uiState.value = DogListUiState.Loading
            when (val response = getDogListUseCase()) {
                is ApiResponse.Success -> {
                    _uiState.value = DogListUiState.Success(response.data)
                }
                is ApiResponse.Error -> {
                    _uiState.value = DogListUiState.Error(response.message)
                }
            }
        }
    }
}
