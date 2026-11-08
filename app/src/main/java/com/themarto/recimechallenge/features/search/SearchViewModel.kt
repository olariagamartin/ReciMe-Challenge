package com.themarto.recimechallenge.features.search

import androidx.lifecycle.ViewModel
import com.themarto.recimechallenge.features.search.model.DietaryPreference
import com.themarto.recimechallenge.features.search.model.FiltersStatus
import com.themarto.recimechallenge.features.search.model.ServingOption
import com.themarto.recimechallenge.model.Recipe
import com.themarto.recimechallenge.ui.preview.PreviewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        loadFakeData()
    }

    private fun loadFakeData() {
        // Simulating loading fake data
        _uiState.value = SearchUiState.Success(
            filtersStatus = FiltersStatus(
                dietaryPreferences = listOf(
                    DietaryPreference("🌿 Vegetarian", false),
                    DietaryPreference("🌱 Vegan", false),
                    DietaryPreference("🌾 Gluten-Free", false),
                    DietaryPreference("🥛 Dairy-Free", false),
                    DietaryPreference("🥑 Keto", false)
                )
            ),
            results = PreviewData.recipeList
        )
    }

    fun onQueryChange(query: String) {
        updateSuccessState { it.copy(searchQuery = query) }
    }

    fun onFilterClick() {
        updateSuccessState { it.copy(filtersOpen = !it.filtersOpen) }
    }

    fun onDietaryPreferenceSelected(preference: DietaryPreference) {
        updateSuccessState { state ->
            val updatedPreferences = state.filtersStatus.dietaryPreferences.map {
                if (it.name == preference.name) it.copy(isSelected = !it.isSelected) else it
            }
            val updatedStatus = state.filtersStatus.copy(dietaryPreferences = updatedPreferences)
            state.copy(
                filtersStatus = updatedStatus,
                filtersApplied = calculateFiltersApplied(updatedStatus)
            )
        }
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
            val resetPreferences = state.filtersStatus.dietaryPreferences.map { it.copy(isSelected = false) }
            val updatedStatus = FiltersStatus(dietaryPreferences = resetPreferences)
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
        count += status.dietaryPreferences.count { it.isSelected }
        if (status.selectedServingOption != ServingOption.ANY) count++
        count += status.includedIngredients.size
        count += status.excludedIngredients.size
        return count
    }
}
