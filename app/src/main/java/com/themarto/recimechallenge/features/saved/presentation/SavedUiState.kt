package com.themarto.recimechallenge.features.saved.presentation

import com.themarto.recimechallenge.core.data.model.Recipe

data class SavedUiState(
    val recipes: List<Recipe> = emptyList(),
    val loading: Boolean = true,
    val error: String? = null
)
