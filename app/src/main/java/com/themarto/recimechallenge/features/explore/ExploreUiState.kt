package com.themarto.recimechallenge.features.explore

import com.themarto.recimechallenge.model.Recipe

sealed class ExploreUiState {
    object Loading : ExploreUiState()
    data class Success(
        val recipes: List<Recipe>,
        val recipeOfTheDay: Recipe
    ) : ExploreUiState()
    // todo: add types
    data class Error(val message: String) : ExploreUiState()
}
