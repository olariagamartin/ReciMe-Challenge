package com.themarto.recimechallenge.features.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.themarto.recimechallenge.components.RecipeCard
import com.themarto.recimechallenge.model.Recipe
import com.themarto.recimechallenge.ui.preview.PreviewData

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier
) {
    // todo:
    //ExploreScreenContent()
}

@Composable
fun ExploreScreenContent(
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit,
    onBookmarkClick: (Recipe) -> Unit,
    recipeOfTheDay: Recipe,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
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
        onBookmarkClick = {}
    )
}