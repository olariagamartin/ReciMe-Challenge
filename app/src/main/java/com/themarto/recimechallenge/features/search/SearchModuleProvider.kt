package com.themarto.recimechallenge.features.search

import com.themarto.recimechallenge.features.search.presentation.SearchViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object SearchModuleProvider {
    fun getModules(): List<Module> = listOf(viewModelModule)
}

val viewModelModule = module {
    viewModel {
        SearchViewModel()
    }
}