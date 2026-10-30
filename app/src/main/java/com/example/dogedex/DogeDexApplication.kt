package com.example.dogedex

import android.app.Application
import com.example.dogedex.di.dogeDexModules
import com.example.dogedex.doglist.presentation.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DogeDexApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DogeDexApplication)
            modules(dogeDexModules + appModule)
        }
    }
}
