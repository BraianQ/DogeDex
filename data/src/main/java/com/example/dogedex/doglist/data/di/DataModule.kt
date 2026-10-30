package com.example.dogedex.doglist.data.di

import com.example.dogedex.doglist.data.DogRepositoryImpl
import com.example.dogedex.doglist.domain.DogRepository
import org.koin.dsl.module

val dataModule = module {
    single<DogRepository> { DogRepositoryImpl() }
}
