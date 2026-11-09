package com.themarto.recimechallenge.features.explore.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.themarto.recimechallenge.components.RecipeCard
import com.themarto.recimechallenge.features.explore.components.FeaturedRecipe
import com.themarto.recimechallenge.features.explore.components.Header
import com.themarto.recimechallenge.data.model.Recipe
import com.themarto.recimechallenge.ui.preview.PreviewData
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel = koinViewModel(),
    onRecipeClick: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val state = uiState) {
        is ExploreUiState.Success -> {
            ExploreScreenContent(
                recipes = state.recipes,
                onRecipeClick = { recipe -> onRecipeClick(recipe.id) },
                onBookmarkClick = { recipe -> viewModel.onBookmarkClick(recipe) },
                recipeOfTheDay = state.recipeOfTheDay,
                onSearchClick = { onSearchClick() },
                dietaryFilters = state.dietaryFilters,
                onDietaryFilterClick = { filter -> viewModel.onDietaryFilterClick(filter) }
            )
        }
        is ExploreUiState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize()
            )
        }
        is ExploreUiState.Error -> {
            Text(
                text = state.message,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun ExploreScreenContent(
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit,
    onBookmarkClick: (Recipe) -> Unit,
    recipeOfTheDay: Recipe,
    onSearchClick: () -> Unit,
    dietaryFilters: List<String>,
    onDietaryFilterClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Header(
                    onSearchClick = onSearchClick,
                    dietaryFilters = dietaryFilters,
                    onDietaryFilterClick = onDietaryFilterClick
                )
            }
            item {
                FeaturedRecipe(
                    recipe = recipeOfTheDay,
                    onClick = { onRecipeClick(recipeOfTheDay) },
                    onBookmarkClick = { onBookmarkClick(recipeOfTheDay) }
                )
            }

            item {
                Text(
                    text = "Explore More",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }

            items(recipes) { recipe ->
                RecipeCard(
                    recipe = recipe,
                    onClick = { onRecipeClick(recipe) },
                    onBookmarkClick = { onBookmarkClick(recipe) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExploreScreenContentPreview() {
    ExploreScreenContent(
        recipes = PreviewData.recipeList,
        onRecipeClick = {},
        recipeOfTheDay = PreviewData.recipe,
        onBookmarkClick = {},
        onSearchClick = {},
        dietaryFilters = listOf("Vegan", "Vegetarian", "Gluten-free"),
        onDietaryFilterClick = {}
    )
}