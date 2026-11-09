package com.themarto.recimechallenge.features.search.presentation

import androidx.lifecycle.ViewModel
import com.themarto.recimechallenge.data.model.DietaryAttribute
import com.themarto.recimechallenge.features.search.model.FiltersStatus
import com.themarto.recimechallenge.features.search.model.ServingOption
import com.themarto.recimechallenge.data.model.Recipe
import com.themarto.recimechallenge.data.repository.RecipesRepository
import com.themarto.recimechallenge.features.search.model.SearchScreenParam
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchViewModel(
    private val recipesRepository: RecipesRepository,
    private val params: SearchScreenParam
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        when (params) {
            is SearchScreenParam.SearchBar -> onSearchBarRequested()
            is SearchScreenParam.Filter -> onFilterRequested(params.filter)
            is SearchScreenParam.None -> onNonRequested()
        }
    }

    private fun onSearchBarRequested() {
        _uiState.update {
            SearchUiState(
                focusSearch = true
            )
        }
    }

    private fun onFilterRequested(filter: DietaryAttribute) {
        _uiState.update {
            SearchUiState(
                filtersStatus = FiltersStatus(
                    dietaryAttrsSelected = listOf(filter)
                ),
                loading = true
            )
        }
    }

    private fun onNonRequested() {
        _uiState.update {
            SearchUiState()
        }
    }

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onFilterClick() {
        _uiState.update { it.copy(filtersOpen = !it.filtersOpen) }
    }

    fun onDietaryPreferenceSelected(preference: DietaryAttribute) {

    }

    fun onServingOptionSelected(option: ServingOption) {
        _uiState.update { state ->
            val updatedStatus = state.filtersStatus.copy(selectedServingOption = option)
            state.copy(
                filtersStatus = updatedStatus,
                filtersApplied = calculateFiltersApplied(updatedStatus)
            )
        }
    }

    fun onRemoveFromIncluded(ingredient: String) {
        _uiState.update { state ->
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
        _uiState.update { state ->
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
        _uiState.update { state ->
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
        _uiState.update { state ->
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
        _uiState.update { state ->
            state.copy(
                filtersStatus = FiltersStatus(),
                filtersApplied = 0
            )
        }
    }

    fun onApplyFilters() {

    }

    fun onBookmarkClick(recipe: Recipe) {
        // Implementation for bookmarking a recipe
    }

    private fun calculateFiltersApplied(status: FiltersStatus): Int {
        var count = 0
        count += status.dietaryAttrsSelected.size
        if (status.selectedServingOption != ServingOption.ANY) count++
        count += status.includedIngredients.size
        count += status.excludedIngredients.size
        return count
    }
}
