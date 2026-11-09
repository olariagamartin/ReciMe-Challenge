package com.themarto.recimechallenge.features.saved

import com.themarto.recimechallenge.features.saved.presentation.SavedViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object SavedModuleProvider {
    fun getModules(): List<Module> = listOf(viewModelModule)
}

val viewModelModule = module {
    viewModel { SavedViewModel(recipesRepository = get()) }
}
