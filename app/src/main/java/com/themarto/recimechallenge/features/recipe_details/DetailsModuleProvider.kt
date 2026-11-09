package com.themarto.recimechallenge.features.recipe_details

import com.themarto.recimechallenge.features.recipe_details.presentation.RecipeDetailsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object DetailsModuleProvider {
    fun getModules(): List<Module> = listOf(viewModelModule)
}

val viewModelModule = module {
    viewModel { recipeId ->
        RecipeDetailsViewModel(
            recipeId = recipeId.get(),
            recipesRepository = get()
        )
    }
}