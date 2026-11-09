package com.themarto.recimechallenge.features.recipe_details.presentation

import com.themarto.recimechallenge.data.model.Recipe

sealed interface RecipeDetailsUiState {
    data object Loading : RecipeDetailsUiState
    data class Success(
        val recipe: Recipe,
        val checkedIngredients: Set<Int> = emptySet()
    ) : RecipeDetailsUiState
    data class Error(val message: String) : RecipeDetailsUiState
}
