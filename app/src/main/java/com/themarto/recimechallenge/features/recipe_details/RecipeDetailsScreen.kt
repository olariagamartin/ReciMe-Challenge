package com.themarto.recimechallenge.features.recipe_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.themarto.recimechallenge.model.Recipe
import com.themarto.recimechallenge.ui.preview.PreviewData
import com.themarto.recimechallenge.ui.theme.ReciMeChallengeTheme

@Composable
fun RecipeDetailsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text("Recipe Details")
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeDetailsContent(
    recipe: Recipe,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ImageCard(
            recipe = recipe,
            onBookmarkClick = onBookmarkClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeDetailsContentPrev() {
    ReciMeChallengeTheme {
        RecipeDetailsContent(
            recipe = PreviewData.recipe,
            onBookmarkClick = {}
        )
    }
}