package com.themarto.recimechallenge.features.recipe_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.themarto.recimechallenge.ui.theme.ReciMeChallengeTheme

@Composable
fun RecipeDetailsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text("Recipe Details")
    }
}

@Composable
fun RecipeDetailsContent(modifier: Modifier = Modifier) {

}

@Preview
@Composable
private fun RecipeDetailsContentPrev() {
    ReciMeChallengeTheme {
        RecipeDetailsContent()
    }
}