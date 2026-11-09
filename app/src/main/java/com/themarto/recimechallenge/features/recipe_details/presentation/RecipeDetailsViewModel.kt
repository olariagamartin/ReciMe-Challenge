package com.themarto.recimechallenge.features.recipe_details.presentation

import androidx.lifecycle.ViewModel
import com.themarto.recimechallenge.ui.preview.PreviewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecipeDetailsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<RecipeDetailsUiState>(RecipeDetailsUiState.Loading)
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    init {
        loadRecipe()
    }

    private fun loadRecipe() {
        // Simulating loading data
        _uiState.value = RecipeDetailsUiState.Success(
            recipe = PreviewData.recipe
        )
    }

    fun onBookmarkClick() {
        // Handle bookmark click
    }

    fun onIngredientCheckedChange(index: Int) {
        val currentState = _uiState.value
        if (currentState is RecipeDetailsUiState.Success) {
            val newCheckedIngredients = currentState.checkedIngredients.toMutableSet()
            if (newCheckedIngredients.contains(index)) {
                newCheckedIngredients.remove(index)
            } else {
                newCheckedIngredients.add(index)
            }
            _uiState.update { currentState.copy(checkedIngredients = newCheckedIngredients) }
        }
    }
}
