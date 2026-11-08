package com.themarto.recimechallenge.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.themarto.recimechallenge.features.search.model.DietaryPreference
import com.themarto.recimechallenge.features.search.model.FiltersStatus
import com.themarto.recimechallenge.features.search.model.ServingOption
import com.themarto.recimechallenge.model.Recipe
import com.themarto.recimechallenge.ui.preview.PreviewData
import com.themarto.recimechallenge.ui.theme.ReciMeChallengeTheme

@Composable
fun SearchScreen(
    onRecipeClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is SearchUiState.Loading -> {
            CircularProgressIndicator(
                modifier = modifier.fillMaxSize()
            )
        }

        is SearchUiState.Success -> {
            SearchScreenContent(
                modifier = modifier,
                searchQuery = state.searchQuery,
                onQueryChange = viewModel::onQueryChange,
                onFilterClick = viewModel::onFilterClick,
                filtersApplied = state.filtersApplied,
                filtersOpen = state.filtersOpen,
                filtersStatus = state.filtersStatus,
                onDietaryPreferenceSelected = viewModel::onDietaryPreferenceSelected,
                onServingOptionSelected = viewModel::onServingOptionSelected,
                onRemoveFromIncluded = viewModel::onRemoveFromIncluded,
                onAddToIncluded = viewModel::onAddToIncluded,
                onRemoveFromExcluded = viewModel::onRemoveFromExcluded,
                onAddToExcluded = viewModel::onAddToExcluded,
                onResetFilters = viewModel::onResetFilters,
                onApplyFilters = viewModel::onApplyFilters,
                onBookmarkClick = viewModel::onBookmarkClick,
                onRecipeClick = onRecipeClick,
                results = state.results
            )
        }

        is SearchUiState.Error -> {
            Text(
                text = state.message,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    filtersApplied: Int,
    filtersOpen: Boolean,
    filtersStatus: FiltersStatus,
    onDietaryPreferenceSelected: (DietaryPreference) -> Unit,
    onServingOptionSelected: (ServingOption) -> Unit,
    onRemoveFromIncluded: (String) -> Unit,
    onAddToIncluded: () -> Unit,
    onRemoveFromExcluded: (String) -> Unit,
    onAddToExcluded: () -> Unit,
    onResetFilters: () -> Unit,
    onApplyFilters: () -> Unit,
    onBookmarkClick: (Recipe) -> Unit,
    onRecipeClick: (String) -> Unit,
    results: List<Recipe>
) {
    Column(
        modifier = modifier.padding(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBar(
                searchQuery = searchQuery,
                onQueryChange = onQueryChange,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilterBtn(
                onClick = onFilterClick,
                selected = filtersOpen,
                filtersApplied = filtersApplied
            )
        }
        if (filtersOpen) {
            Spacer(modifier = Modifier.height(8.dp))
            FiltersCard(
                filtersStatus = filtersStatus,
                onDietaryPreferenceSelected = onDietaryPreferenceSelected,
                onServingOptionSelected = onServingOptionSelected,
                onRemoveFromIncluded = onRemoveFromIncluded,
                onAddToIncluded = onAddToIncluded,
                onRemoveFromExcluded = onRemoveFromExcluded,
                onAddToExcluded = onAddToExcluded,
                onResetFilters = onResetFilters,
                onApplyFilters = onApplyFilters
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        SearchResult(
            results = results,
            onRecipeClick = onRecipeClick,
            onBookmarkClick = onBookmarkClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenContentPreview() {
    ReciMeChallengeTheme {
        SearchScreenContent(
            searchQuery = "Pasta",
            onQueryChange = {},
            onFilterClick = {},
            filtersApplied = 2,
            filtersOpen = true,
            filtersStatus = FiltersStatus(
                dietaryPreferences = listOf(
                    DietaryPreference("🌿 Vegetarian", true),
                    DietaryPreference("🌱 Vegan", false),
                    DietaryPreference("🌾 Gluten-Free", false),
                    DietaryPreference("🥛 Dairy-Free", false),
                    DietaryPreference("🥑 Keto", false)
                ),
                selectedServingOption = ServingOption.ONE_TWO,
                includedIngredients = listOf("Potatoes", "Carrots"),
                excludedIngredients = listOf("Spinach"),
            ),
            onDietaryPreferenceSelected = {},
            onServingOptionSelected = {},
            onRemoveFromIncluded = {},
            onAddToIncluded = {},
            onRemoveFromExcluded = {},
            onAddToExcluded = {},
            onResetFilters = {},
            onApplyFilters = {},
            onBookmarkClick = {},
            onRecipeClick = {},
            results = PreviewData.recipeList
        )
    }
}
