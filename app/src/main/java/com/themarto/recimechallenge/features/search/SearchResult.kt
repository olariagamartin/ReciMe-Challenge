package com.themarto.recimechallenge.features.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.themarto.recimechallenge.components.RecipeCard
import com.themarto.recimechallenge.model.Recipe
import com.themarto.recimechallenge.ui.preview.PreviewData
import com.themarto.recimechallenge.ui.theme.ReciMeChallengeTheme

@Composable
fun SearchResult(
    results: List<Recipe>,
    onRecipeClick: (String) -> Unit,
    onBookmarkClick: (Recipe) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(results) { recipe ->
            RecipeCard(
                recipe = recipe,
                onClick = { onRecipeClick(recipe.id) },
                onBookmarkClick = { onBookmarkClick(recipe) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultPreview() {
    ReciMeChallengeTheme {
        SearchResult(
            results = PreviewData.recipeList,
            onRecipeClick = {},
            onBookmarkClick = {}
        )
    }
}