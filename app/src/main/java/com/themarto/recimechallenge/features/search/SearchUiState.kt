package com.themarto.recimechallenge.features.search

import com.themarto.recimechallenge.features.search.model.FiltersStatus

sealed class SearchUiState {
    data object Loading : SearchUiState()
    data class Success(
        val searchQuery: String = "",
        val filtersApplied: Int = 0,
        val filtersOpen: Boolean = false,
        val filtersStatus: FiltersStatus = FiltersStatus()
    ) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}
