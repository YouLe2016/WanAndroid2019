package com.wyl.category

import com.wyl.category.category.CategoryChildViewModel
import com.wyl.category.category.CategoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CategoryChildViewModel() }
    viewModel { CategoryViewModel() }
}

val categoryModule = listOf(viewModelModule)