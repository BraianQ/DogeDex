package com.example.dogedex.di

import com.example.dogedex.doglist.data.di.dataModule
import com.example.dogedex.doglist.domain.di.domainModule

val dogeDexModules = listOf(
    domainModule,
    dataModule
)
