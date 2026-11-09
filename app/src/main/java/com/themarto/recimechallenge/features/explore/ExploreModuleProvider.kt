package com.themarto.recimechallenge.features.explore

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object ExploreModuleProvider {
    fun getModules(): List<Module> = listOf(viewModelModule)
}

val viewModelModule = module {
    viewModel { ExploreViewModel() }
}