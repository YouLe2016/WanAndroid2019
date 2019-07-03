package com.wyl.base

import com.wyl.base.repository.ArticleRepository
import org.koin.dsl.module

val viewModelModule = module {
    single { ArticleRepository() }
}

val baseModule = listOf(viewModelModule)