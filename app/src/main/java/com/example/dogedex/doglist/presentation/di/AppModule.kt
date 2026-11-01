package com.example.dogedex.doglist.presentation.di

import com.example.dogedex.MainViewModel
import com.example.dogedex.auth.presentation.LoginViewModel
import com.example.dogedex.auth.presentation.SignUpViewModel
import com.example.dogedex.doglist.presentation.DogListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DogListViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
}
