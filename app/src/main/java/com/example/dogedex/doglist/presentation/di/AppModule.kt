package com.example.dogedex.doglist.presentation.di

import com.example.dogedex.MainViewModel
import com.example.dogedex.auth.presentation.LoginViewModel
import com.example.dogedex.auth.presentation.SignUpViewModel
import com.example.dogedex.doglist.presentation.DogListViewModel
import com.example.dogedex.machinelearning.Classifier
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Classifier(androidContext()) }
    viewModel { MainViewModel(get()) }
    viewModel { DogListViewModel(get(), get(), get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
}
