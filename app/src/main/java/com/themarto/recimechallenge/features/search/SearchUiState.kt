package com.themarto.recimechallenge.features.search

import com.themarto.recimechallenge.features.search.model.FiltersStatus
import com.themarto.recimechallenge.model.Recipe

sealed class SearchUiState {
    data object Loading : SearchUiState()
    data class Success(
        val searchQuery: String = "",
        val filtersApplied: Int = 0,
        val filtersOpen: Boolean = false,
        val filtersStatus: FiltersStatus = FiltersStatus(),
        val results: List<Recipe> = emptyList()
    ) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}
