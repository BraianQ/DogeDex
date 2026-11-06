package com.example.dogedex.doglist.domain.di

import com.example.dogedex.auth.domain.GetLoggedInUserUseCase
import com.example.dogedex.auth.domain.LoginUseCase
import com.example.dogedex.auth.domain.LogoutUseCase
import com.example.dogedex.auth.domain.SignUpUseCase
import com.example.dogedex.doglist.domain.AddDogToUserUseCase
import com.example.dogedex.doglist.domain.GetDogByMlIdUseCase
import com.example.dogedex.doglist.domain.GetDogCollectionUseCase
import com.example.dogedex.doglist.domain.GetDogListUseCase
import com.example.dogedex.doglist.domain.GetUserDogsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetDogListUseCase(get()) }
    factory { GetUserDogsUseCase(get()) }
    factory { GetDogCollectionUseCase(get()) }
    factory { GetDogByMlIdUseCase(get()) }
    factory { AddDogToUserUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { SignUpUseCase(get()) }
    factory { GetLoggedInUserUseCase(get()) }
    factory { LogoutUseCase(get()) }
}
