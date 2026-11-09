package com.themarto.recimechallenge.features.search.model

data class FiltersStatus(
    val dietaryItems: List<DietaryItem> = emptyList(),
    val selectedServingOption: ServingOption = ServingOption.ANY,
    val includedIngredients: List<String> = emptyList(),
    val excludedIngredients: List<String> = emptyList()
)
