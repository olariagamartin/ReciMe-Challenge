package com.themarto.recimechallenge.features.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.themarto.recimechallenge.R
import com.themarto.recimechallenge.core.components.RecipeCard
import com.themarto.recimechallenge.core.data.model.Recipe
import com.themarto.recimechallenge.features.saved.presentation.SavedViewModel
import com.themarto.recimechallenge.core.ui.preview.PreviewData
import com.themarto.recimechallenge.core.ui.theme.ReciMeChallengeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SavedScreen(
    onRecipeClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SavedViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when {
        uiState.loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        else -> {
            SavedScreenContent(
                recipes = uiState.recipes,
                onRecipeClick = onRecipeClick,
                onBookmarkClick = viewModel::onBookmarkClick,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun SavedScreenContent(
    recipes: List<Recipe>,
    onRecipeClick: (String) -> Unit,
    onBookmarkClick: (Recipe) -> Unit,
    modifier: Modifier = Modifier,
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
                Text(
                    text = stringResource(R.string.saved_header_title),
                    style = MaterialTheme.typography.headlineLarge,
                )
                Text(
                    text = pluralStringResource(
                        R.plurals.saved_recipes_count,
                        recipes.size,
                        recipes.size
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                )
            }
            if (recipes.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.saved_empty),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }
            }
            items(recipes, key = { it.id }) { recipe ->
                RecipeCard(
                    recipe = recipe,
                    onClick = { onRecipeClick(recipe.id) },
                    onBookmarkClick = { onBookmarkClick(recipe) },
                    showRating = false,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SavedScreenContentPreview() {
    ReciMeChallengeTheme {
        SavedScreenContent(
            recipes = PreviewData.recipeList,
            onRecipeClick = {},
            onBookmarkClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SavedScreenEmptyPreview() {
    ReciMeChallengeTheme {
        SavedScreenContent(
            recipes = emptyList(),
            onRecipeClick = {},
            onBookmarkClick = {},
        )
    }
}
