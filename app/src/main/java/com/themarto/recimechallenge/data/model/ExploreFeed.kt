package com.themarto.recimechallenge.data.model

data class ExploreFeed(
    val recipeOfTheDay: Recipe,
    val recommended: List<Recipe>,
)