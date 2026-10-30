package com.example.dogedex.doglist.domain.di

import com.example.dogedex.doglist.domain.GetDogListUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetDogListUseCase(get()) }
}
