package com.themarto.recimechallenge.data

data class ExploreFeed(
    val recipeOfTheDay: Recipe,
    val recommended: List<Recipe>,
)