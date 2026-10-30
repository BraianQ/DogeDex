package com.example.dogedex.doglist.presentation.di

import com.example.dogedex.doglist.presentation.DogListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { DogListViewModel(get()) }
}
