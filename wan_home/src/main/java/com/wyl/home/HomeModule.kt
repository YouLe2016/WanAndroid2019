package com.wyl.home

import com.wyl.base.repository.ArticleRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}

val homeModule = listOf(viewModelModule)