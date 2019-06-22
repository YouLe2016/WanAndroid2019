package com.wyl.category

import com.wyl.category.vm.CategoryChildViewModel
import com.wyl.category.vm.CategoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CategoryChildViewModel() }
    viewModel { CategoryViewModel() }
}

val categoryModule = listOf(viewModelModule)