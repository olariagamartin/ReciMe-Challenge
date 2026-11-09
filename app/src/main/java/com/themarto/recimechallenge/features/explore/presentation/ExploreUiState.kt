package com.themarto.recimechallenge.features.explore.presentation

import com.themarto.recimechallenge.data.Recipe

sealed class ExploreUiState {
    object Loading : ExploreUiState()
    data class Success(
        val recipes: List<Recipe>,
        val recipeOfTheDay: Recipe,
        val dietaryFilters: List<String> = emptyList()
    ) : ExploreUiState()
    // todo: add types
    data class Error(val message: String) : ExploreUiState()
}
