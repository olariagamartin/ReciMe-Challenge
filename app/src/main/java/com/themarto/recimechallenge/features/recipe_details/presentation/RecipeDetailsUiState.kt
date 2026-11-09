package com.themarto.recimechallenge.features.recipe_details.presentation

import com.themarto.recimechallenge.data.Recipe

sealed interface RecipeDetailsUiState {
    data object Loading : RecipeDetailsUiState
    data class Success(
        val recipe: Recipe,
        val isBookmarked: Boolean = false,
        val checkedIngredients: Set<Int> = emptySet()
    ) : RecipeDetailsUiState
    data class Error(val message: String) : RecipeDetailsUiState
}
