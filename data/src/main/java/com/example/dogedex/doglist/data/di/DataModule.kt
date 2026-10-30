package com.example.dogedex.doglist.data.di

import com.example.dogedex.doglist.data.DogRepositoryImpl
import com.example.dogedex.doglist.data.api.BASE_URL
import com.example.dogedex.doglist.data.api.DogApiService
import com.example.dogedex.doglist.domain.DogRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single<DogApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApiService::class.java)
    }

    single<DogRepository> { DogRepositoryImpl(get()) }
}
