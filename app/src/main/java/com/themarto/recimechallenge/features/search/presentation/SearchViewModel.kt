package com.themarto.recimechallenge.features.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themarto.recimechallenge.data.model.DietaryAttribute
import com.themarto.recimechallenge.features.search.model.FiltersStatus
import com.themarto.recimechallenge.features.search.model.ServingOption
import com.themarto.recimechallenge.data.model.Recipe
import com.themarto.recimechallenge.data.model.RecipeSearch
import com.themarto.recimechallenge.data.model.isError
import com.themarto.recimechallenge.data.repository.RecipesRepository
import com.themarto.recimechallenge.features.search.model.SearchScreenParam
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val recipesRepository: RecipesRepository,
    private val params: SearchScreenParam,
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
                    dietaryAttrsSelected = listOf(filter),
                ),
                filtersApplied = 1
            )
        }
        searchRecipes(
            RecipeSearch(
                dietaryPreferences = listOf(filter)
            )
        )
    }

    private fun searchRecipes(search: RecipeSearch) {
        _uiState.update {
            it.copy(
                loading = true
            )
        }
        viewModelScope.launch {
            recipesRepository.searchRecipes(search).collect { result ->
                if (result.isError()) {
                    _uiState.update {
                        it.copy(
                            loading = false,
                            error = result.message
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            loading = false,
                            results = result.value
                        )
                    }
                }
            }
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
        _uiState.update { state ->
            val updatedStatus = state.filtersStatus.copy(
                dietaryAttrsSelected = if (preference in state.filtersStatus.dietaryAttrsSelected) {
                    state.filtersStatus.dietaryAttrsSelected - preference
                } else {
                    state.filtersStatus.dietaryAttrsSelected + preference
                }
            )
            state.copy(
                filtersStatus = updatedStatus,
                filtersApplied = calculateFiltersApplied(updatedStatus)
            )
        }
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
            val updatedIngredients =
                state.filtersStatus.includedIngredients.filter { it != ingredient }
            val updatedStatus = state.filtersStatus.copy(includedIngredients = updatedIngredients)
            state.copy(
                filtersStatus = updatedStatus,
                filtersApplied = calculateFiltersApplied(updatedStatus)
            )
        }
    }

    fun onAddToIncluded(ingredient: String) {
        _uiState.update { state ->
            val updatedStatus = state.filtersStatus.copy(
                includedIngredients = state.filtersStatus.includedIngredients + ingredient
            )
            state.copy(
                filtersStatus = updatedStatus,
                filtersApplied = calculateFiltersApplied(updatedStatus)
            )
        }
    }

    fun onRemoveFromExcluded(ingredient: String) {
        _uiState.update { state ->
            val updatedIngredients =
                state.filtersStatus.excludedIngredients.filter { it != ingredient }
            val updatedStatus = state.filtersStatus.copy(excludedIngredients = updatedIngredients)
            state.copy(
                filtersStatus = updatedStatus,
                filtersApplied = calculateFiltersApplied(updatedStatus)
            )
        }
    }

    fun onAddToExcluded(ingredient: String) {
        _uiState.update { state ->
            val updatedStatus = state.filtersStatus.copy(
                excludedIngredients = state.filtersStatus.excludedIngredients + ingredient
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
        _uiState.update { state ->
            state.copy(
                filtersOpen = false,
                loading = true,

            )
        }
        searchRecipes(
            RecipeSearch(
                query = _uiState.value.searchQuery,
                dietaryPreferences = _uiState.value.filtersStatus.dietaryAttrsSelected,
                servingOption = _uiState.value.filtersStatus.selectedServingOption,
                includeIngredients = _uiState.value.filtersStatus.includedIngredients,
                excludeIngredients = _uiState.value.filtersStatus.excludedIngredients,
            )
        )
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
