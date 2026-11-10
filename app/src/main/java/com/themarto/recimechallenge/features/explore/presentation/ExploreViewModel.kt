package com.themarto.recimechallenge.features.explore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themarto.recimechallenge.core.data.model.DietaryAttribute
import com.themarto.recimechallenge.core.data.model.ExploreFeed
import com.themarto.recimechallenge.core.data.model.Recipe
import com.themarto.recimechallenge.core.data.model.Result
import com.themarto.recimechallenge.core.data.model.isSuccess
import com.themarto.recimechallenge.core.data.repository.RecipesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    val uiState: StateFlow<ExploreUiState> = recipesRepository.getExploreFeed()
        .map(::mapResultToUiState)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExploreUiState.Loading)

    fun onBookmarkClick(recipe: Recipe) {
        viewModelScope.launch {
            if (recipe.saved) {
                recipesRepository.deleteRecipe(recipe.id.toInt())
            } else {
                recipesRepository.saveRecipe(recipe)
            }
        }
    }

    private suspend fun mapResultToUiState(result: Result<ExploreFeed>): ExploreUiState {
        return if (result.isSuccess()) {
            ExploreUiState.Success(
                recipes = result.value.recommended,
                recipeOfTheDay = result.value.recipeOfTheDay,
            )
        } else {
            ExploreUiState.Error(result.message)
        }
    }
}
