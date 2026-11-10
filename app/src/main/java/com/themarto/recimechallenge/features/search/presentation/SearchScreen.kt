package com.themarto.recimechallenge.features.search.presentation

import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.themarto.recimechallenge.R
import com.themarto.recimechallenge.core.data.model.DietaryAttribute
import com.themarto.recimechallenge.features.search.components.FilterBtn
import com.themarto.recimechallenge.features.search.components.FiltersCard
import com.themarto.recimechallenge.features.search.components.SearchBar
import com.themarto.recimechallenge.features.search.components.SearchResult
import com.themarto.recimechallenge.features.search.model.FiltersStatus
import com.themarto.recimechallenge.features.search.model.ServingOption
import com.themarto.recimechallenge.core.data.model.Recipe
import com.themarto.recimechallenge.features.search.components.IngredientPromptDialog
import com.themarto.recimechallenge.features.search.model.SearchScreenParam
import com.themarto.recimechallenge.core.ui.preview.PreviewData
import com.themarto.recimechallenge.core.ui.theme.ReciMeChallengeTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SearchScreen(
    onRecipeClick: (String) -> Unit,
    params: SearchScreenParam,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel{ parametersOf(params) }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        SearchScreenContent(
            modifier = modifier,
            uiState = uiState,
            onQueryChange = viewModel::onQueryChange,
            onFilterClick = viewModel::onFilterClick,
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
            onSearchClick = viewModel::onSearch
        )
}

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    onDietaryPreferenceSelected: (DietaryAttribute) -> Unit,
    onServingOptionSelected: (ServingOption) -> Unit,
    onRemoveFromIncluded: (String) -> Unit,
    onAddToIncluded: (String) -> Unit,
    onRemoveFromExcluded: (String) -> Unit,
    onAddToExcluded: (String) -> Unit,
    onResetFilters: () -> Unit,
    onApplyFilters: () -> Unit,
    onBookmarkClick: (Recipe) -> Unit,
    onRecipeClick: (String) -> Unit,
    onSearchClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBar(
                searchQuery = uiState.searchQuery,
                onQueryChange = onQueryChange,
                initialFocus = uiState.focusSearch,
                modifier = Modifier.weight(1f),
                onSearchClick = onSearchClick
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilterBtn(
                onClick = onFilterClick,
                selected = uiState.filtersOpen,
                filtersApplied = uiState.filtersApplied
            )
        }
        if (uiState.filtersOpen) {
            Spacer(modifier = Modifier.height(8.dp))
            FiltersCard(
                filtersStatus = uiState.filtersStatus,
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
        when {
            uiState.loading -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Text(
                    text = uiState.error,
                    modifier = modifier.fillMaxSize()
                )
            }
            else -> {
                SearchResult(
                    results = uiState.results,
                    onRecipeClick = onRecipeClick,
                    onBookmarkClick = onBookmarkClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenContentPreview() {
    ReciMeChallengeTheme {
        val uiState = SearchUiState(
            searchQuery = "Pasta",
            filtersApplied = 2,
            filtersOpen = true,
            filtersStatus = FiltersStatus(
                dietaryAttrsSelected = listOf(
                    DietaryAttribute.VEGETARIAN,
                    DietaryAttribute.KETO,
                ),
                selectedServingOption = ServingOption.ONE_TWO,
                includedIngredients = listOf("Potatoes", "Carrots"),
                excludedIngredients = listOf("Spinach"),
            ),
            results = PreviewData.recipeList,
            loading = false,
            error = null,
        )

        SearchScreenContent(
            uiState = uiState,
            onQueryChange = {},
            onFilterClick = {},
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
            onSearchClick = {}
        )
    }
}
