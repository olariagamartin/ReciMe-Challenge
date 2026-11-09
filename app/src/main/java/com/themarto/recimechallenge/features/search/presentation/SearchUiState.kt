package com.themarto.recimechallenge.features.search.presentation

import com.themarto.recimechallenge.features.search.model.FiltersStatus
import com.themarto.recimechallenge.data.model.Recipe

sealed class SearchUiState {
    data object Loading : SearchUiState()
    data class Success(
        val searchQuery: String = "",
        val filtersApplied: Int = 0,
        val filtersOpen: Boolean = false,
        val filtersStatus: FiltersStatus = FiltersStatus(),
        val results: List<Recipe> = emptyList(),
        val focusSearch: Boolean = false
    ) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}
