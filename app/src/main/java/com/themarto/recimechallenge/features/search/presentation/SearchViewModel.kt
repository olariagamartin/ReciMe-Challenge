package com.themarto.recimechallenge.features.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.themarto.recimechallenge.data.model.DietaryAttribute
import com.themarto.recimechallenge.features.search.model.DietaryItem
import com.themarto.recimechallenge.features.search.model.FiltersStatus
import com.themarto.recimechallenge.features.search.model.ServingOption
import com.themarto.recimechallenge.data.model.Recipe
import com.themarto.recimechallenge.data.repository.RecipesRepository
import com.themarto.recimechallenge.features.search.model.SearchScreenParam
import com.themarto.recimechallenge.ui.preview.PreviewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchViewModel(
    private val recipesRepository: RecipesRepository,
    private val params: SearchScreenParam
) : ViewModel() {
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            SearchUiState.Success(
                searchQuery = "",
                filtersApplied = 0,
                filtersOpen = true,
                filtersStatus = FiltersStatus(),
                results = emptyList()
            )
        }
        when (params) {
            is SearchScreenParam.SearchBar -> onSearchBarRequested()
            is SearchScreenParam.Filter -> {

            }
            is SearchScreenParam.None -> {}
        }
    }

    private fun onSearchBarRequested() {
        _uiState.update {
            SearchUiState.Success(
                searchQuery = "",
                filtersApplied = 0,
                filtersOpen = false,
                filtersStatus = FiltersStatus(),
                results = emptyList(),
                focusSearch = true
            )
        }
    }

    fun onQueryChange(query: String) {
        updateSuccessState { it.copy(searchQuery = query) }
    }

    fun onFilterClick() {
        updateSuccessState { it.copy(filtersOpen = !it.filtersOpen) }
    }

    fun onDietaryPreferenceSelected(preference: DietaryItem) {

    }

    fun onServingOptionSelected(option: ServingOption) {
        updateSuccessState { state ->
            val updatedStatus = state.filtersStatus.copy(selectedServingOption = option)
            state.copy(
                filtersStatus = updatedStatus,
                filtersApplied = calculateFiltersApplied(updatedStatus)
            )
        }
    }

    fun onRemoveFromIncluded(ingredient: String) {
        updateSuccessState { state ->
            val updatedIngredients = state.filtersStatus.includedIngredients.filter { it != ingredient }
            val updatedStatus = state.filtersStatus.copy(includedIngredients = updatedIngredients)
            state.copy(
                filtersStatus = updatedStatus,
                filtersApplied = calculateFiltersApplied(updatedStatus)
            )
        }
    }

    fun onAddToIncluded() {
        // Implementation for adding ingredient: adding a fake one for now
        updateSuccessState { state ->
            val newIngredient = "Ingredient ${state.filtersStatus.includedIngredients.size + 1}"
            val updatedStatus = state.filtersStatus.copy(
                includedIngredients = state.filtersStatus.includedIngredients + newIngredient
            )
            state.copy(
                filtersStatus = updatedStatus,
                filtersApplied = calculateFiltersApplied(updatedStatus)
            )
        }
    }

    fun onRemoveFromExcluded(ingredient: String) {
        updateSuccessState { state ->
            val updatedIngredients = state.filtersStatus.excludedIngredients.filter { it != ingredient }
            val updatedStatus = state.filtersStatus.copy(excludedIngredients = updatedIngredients)
            state.copy(
                filtersStatus = updatedStatus,
                filtersApplied = calculateFiltersApplied(updatedStatus)
            )
        }
    }

    fun onAddToExcluded() {
        // Implementation for adding ingredient: adding a fake one for now
        updateSuccessState { state ->
            val newIngredient = "Excluded ${state.filtersStatus.excludedIngredients.size + 1}"
            val updatedStatus = state.filtersStatus.copy(
                excludedIngredients = state.filtersStatus.excludedIngredients + newIngredient
            )
            state.copy(
                filtersStatus = updatedStatus,
                filtersApplied = calculateFiltersApplied(updatedStatus)
            )
        }
    }

    fun onResetFilters() {
        updateSuccessState { state ->
            val resetPreferences = state.filtersStatus.dietaryItems.map { it.copy(isSelected = false) }
            val updatedStatus = FiltersStatus(dietaryItems = resetPreferences)
            state.copy(
                filtersStatus = updatedStatus,
                filtersApplied = 0
            )
        }
    }

    fun onApplyFilters() {

    }

    fun onBookmarkClick(recipe: Recipe) {
        // Implementation for bookmarking a recipe
    }


    private fun updateSuccessState(update: (SearchUiState.Success) -> SearchUiState.Success) {
        _uiState.update { currentState ->
            if (currentState is SearchUiState.Success) {
                update(currentState)
            } else {
                currentState
            }
        }
    }

    private fun calculateFiltersApplied(status: FiltersStatus): Int {
        var count = 0
        count += status.dietaryItems.count { it.isSelected }
        if (status.selectedServingOption != ServingOption.ANY) count++
        count += status.includedIngredients.size
        count += status.excludedIngredients.size
        return count
    }
}
