package com.themarto.recimechallenge.features.search.model

import com.themarto.recimechallenge.core.data.model.DietaryAttribute

data class FiltersStatus(
    val dietaryAttrsSelected: List<DietaryAttribute> = emptyList(),
    val selectedServingOption: ServingOption = ServingOption.ANY,
    val includedIngredients: List<String> = emptyList(),
    val excludedIngredients: List<String> = emptyList()
)
