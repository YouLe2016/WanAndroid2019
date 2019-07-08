package com.wyl.base

import com.wyl.base.fragment.ArticleTypeViewModel
import com.wyl.base.repository.ArticleRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { ArticleRepository() }
    viewModel { (id: Int) -> ArticleTypeViewModel(id) }
    viewModel { CommonViewModel(get()) }
}

val baseModule = listOf(viewModelModule)