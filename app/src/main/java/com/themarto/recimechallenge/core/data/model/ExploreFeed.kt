package com.themarto.recimechallenge.core.data.model

data class ExploreFeed(
    val recipeOfTheDay: Recipe,
    val recommended: List<Recipe>,
)