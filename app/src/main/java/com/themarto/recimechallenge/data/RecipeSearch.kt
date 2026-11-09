package com.themarto.recimechallenge.data

data class RecipeSearch(
    val query: String = "",
    val dietaryPreferences: List<String> = emptyList(),
    val minServings: Int? = null,
    val maxServings: Int? = null,
    val includeIngredients: List<String> = emptyList(),
    val excludeIngredients: List<String> = emptyList(),
)
