package com.wyl.login

import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel() }
}

val appModule = listOf(viewModelModule)