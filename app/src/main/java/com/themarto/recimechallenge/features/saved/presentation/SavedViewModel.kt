package com.themarto.recimechallenge.features.saved.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themarto.recimechallenge.core.data.model.Recipe
import com.themarto.recimechallenge.core.data.repository.RecipesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SavedViewModel(
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    val uiState: StateFlow<SavedUiState> = recipesRepository.getAllSavedRecipes()
        .map(::mapResultToUiState)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SavedUiState(loading = true))

    private suspend fun mapResultToUiState(result: List<Recipe>): SavedUiState {
        return SavedUiState(
            recipes = result,
            loading = false
        )
    }

    fun onBookmarkClick(recipe: Recipe) {
        viewModelScope.launch {
            if (recipe.saved) {
                recipesRepository.deleteRecipe(recipe.id.toInt())
            } else {
                recipesRepository.saveRecipe(recipe)
            }
        }
    }
}
