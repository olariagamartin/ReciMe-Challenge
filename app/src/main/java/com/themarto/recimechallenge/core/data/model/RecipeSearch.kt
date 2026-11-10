package com.themarto.recimechallenge.core.data.model

import com.themarto.recimechallenge.features.search.model.ServingOption

data class RecipeSearch(
    val query: String = "",
    val dietaryPreferences: List<DietaryAttribute> = emptyList(),
    val servingOption: ServingOption = ServingOption.ANY,
    val includeIngredients: List<String> = emptyList(),
    val excludeIngredients: List<String> = emptyList(),
)