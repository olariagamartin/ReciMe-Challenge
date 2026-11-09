package com.themarto.recimechallenge.features.search.presentation

import com.themarto.recimechallenge.features.search.model.FiltersStatus
import com.themarto.recimechallenge.data.model.Recipe

data class SearchUiState(
    val searchQuery: String = "",
    val filtersApplied: Int = 0,
    val filtersOpen: Boolean = false,
    val filtersStatus: FiltersStatus = FiltersStatus(),
    val results: List<Recipe> = emptyList(),
    val focusSearch: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null,
)
