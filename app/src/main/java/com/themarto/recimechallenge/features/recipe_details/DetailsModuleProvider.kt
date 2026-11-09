package com.themarto.recimechallenge.features.recipe_details

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object DetailsModuleProvider {
    fun getModules(): List<Module> = listOf(viewModelModule)
}

val viewModelModule = module {
    viewModel {
        RecipeDetailsViewModel()
    }
}