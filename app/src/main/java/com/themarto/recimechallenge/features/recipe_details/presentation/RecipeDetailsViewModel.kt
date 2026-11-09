package com.themarto.recimechallenge.features.recipe_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themarto.recimechallenge.data.model.isError
import com.themarto.recimechallenge.data.repository.RecipesRepository
import com.themarto.recimechallenge.ui.preview.PreviewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    private val recipeId: String,
    private val recipesRepository: RecipesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<RecipeDetailsUiState>(RecipeDetailsUiState.Loading)
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    init {
        loadRecipe()
    }

    private fun loadRecipe() {
        viewModelScope.launch {
            recipesRepository.getRecipe(recipeId.toInt()).collect { result ->
                if (result.isError()) {
                    _uiState.update { RecipeDetailsUiState.Error(result.message) }
                } else {
                    _uiState.update {
                        RecipeDetailsUiState.Success(
                            recipe = result.value,
                        )
                    }
                }
            }
        }
    }

    fun onBookmarkClick() {
        val currentState = _uiState.value
        if (currentState !is RecipeDetailsUiState.Success) return
        viewModelScope.launch {
            if (currentState.recipe.saved) {
                recipesRepository.deleteRecipe(currentState.recipe.id.toInt())
            } else {
                recipesRepository.saveRecipe(currentState.recipe)
            }
        }
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
