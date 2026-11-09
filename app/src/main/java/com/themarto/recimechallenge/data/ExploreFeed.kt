package com.themarto.recimechallenge.data

import com.themarto.recimechallenge.model.Recipe

data class ExploreFeed(
    val recipeOfTheDay: Recipe,
    val recommended: List<Recipe>,
)