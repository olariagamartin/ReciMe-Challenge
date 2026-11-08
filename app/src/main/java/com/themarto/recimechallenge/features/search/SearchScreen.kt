package com.themarto.recimechallenge.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.themarto.recimechallenge.features.search.model.DietaryPreference
import com.themarto.recimechallenge.features.search.model.FiltersStatus
import com.themarto.recimechallenge.features.search.model.ServingOption
import com.themarto.recimechallenge.ui.theme.ReciMeChallengeTheme

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {

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
    onResetFilters: () -> Unit
) {
    Column(modifier = modifier) {
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
        FiltersCard(
            filtersStatus = filtersStatus,
            onDietaryPreferenceSelected = onDietaryPreferenceSelected,
            onServingOptionSelected = onServingOptionSelected,
            onRemoveFromIncluded = onRemoveFromIncluded,
            onAddToIncluded = onAddToIncluded,
            onRemoveFromExcluded = onRemoveFromExcluded,
            onAddToExcluded = onAddToExcluded,
            onResetFilters = onResetFilters
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
                    DietaryPreference("\uD83C\uDF3F Vegetarian", true),
                    DietaryPreference("\uD83C\uDF31 Vegan", false),
                    DietaryPreference("\uD83C\uDF3E Gluten-Free", false),
                    DietaryPreference("\uD83E\uDD5B Dairy-Free", false),
                    DietaryPreference("\uD83E\uDD51 Keto", false)
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
            onResetFilters = {}
        )
    }
}
