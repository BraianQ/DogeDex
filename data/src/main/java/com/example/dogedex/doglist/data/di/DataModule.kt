package com.example.dogedex.doglist.data.di

import com.example.dogedex.auth.data.AuthRepositoryImpl
import com.example.dogedex.auth.data.UserLocalDataSource
import com.example.dogedex.auth.data.api.AuthApiService
import com.example.dogedex.auth.domain.AuthRepository
import com.example.dogedex.doglist.data.DogRepositoryImpl
import com.example.dogedex.doglist.data.api.ApiServiceInterceptor
import com.example.dogedex.doglist.data.api.BASE_URL
import com.example.dogedex.doglist.data.api.DogApiService
import com.example.dogedex.doglist.domain.DogRepository
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single { UserLocalDataSource(androidContext()) }

    single { ApiServiceInterceptor(get()) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<ApiServiceInterceptor>())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<DogApiService> { get<Retrofit>().create(DogApiService::class.java) }
    single<AuthApiService> { get<Retrofit>().create(AuthApiService::class.java) }

    single<DogRepository> { DogRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}
