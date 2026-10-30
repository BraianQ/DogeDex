package com.example.dogedex.doglist.presentation

import androidx.lifecycle.ViewModel
import com.example.dogedex.doglist.domain.Dog
import com.example.dogedex.doglist.domain.GetDogListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DogListViewModel(
    private val getDogListUseCase: GetDogListUseCase
) : ViewModel() {

    private val _dogList = MutableStateFlow<List<Dog>>(emptyList())
    val dogList: StateFlow<List<Dog>> = _dogList.asStateFlow()

    init {
        getDogList()
    }

    private fun getDogList() {
        _dogList.value = getDogListUseCase()
    }
}
