package com.example.dogedex.doglist.data.di

import com.example.dogedex.auth.data.AuthRepositoryImpl
import com.example.dogedex.auth.data.api.AuthApiService
import com.example.dogedex.auth.domain.AuthRepository
import com.example.dogedex.doglist.data.DogRepositoryImpl
import com.example.dogedex.doglist.data.api.BASE_URL
import com.example.dogedex.doglist.data.api.DogApiService
import com.example.dogedex.doglist.domain.DogRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<DogApiService> { get<Retrofit>().create(DogApiService::class.java) }
    single<AuthApiService> { get<Retrofit>().create(AuthApiService::class.java) }

    single<DogRepository> { DogRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}
