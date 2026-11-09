package com.themarto.recimechallenge.features.explore.presentation

import androidx.lifecycle.ViewModel
import com.themarto.recimechallenge.data.Recipe
import com.themarto.recimechallenge.ui.preview.PreviewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ExploreViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<ExploreUiState>(ExploreUiState.Loading)
    val uiState: StateFlow<ExploreUiState> = _uiState

    init {
        getDiscoverFeed()
    }

    private fun getDiscoverFeed() {
        _uiState.value = ExploreUiState.Success(
            recipes = PreviewData.recipeList,
            recipeOfTheDay = PreviewData.recipe,
            dietaryFilters = listOf("Vegan", "Vegetarian", "Gluten-free", "Dairy-free")
        )
    }

    fun onBookmarkClick(recipe: Recipe) {
        
    }

    fun onDietaryFilterClick(filter: String) {

    }
}
