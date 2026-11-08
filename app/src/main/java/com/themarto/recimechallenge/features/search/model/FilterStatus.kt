package com.themarto.recimechallenge.features.search.model

import com.themarto.recimechallenge.features.search.model.DietaryPreference
import com.themarto.recimechallenge.features.search.model.ServingOption

data class FiltersStatus(
    val dietaryPreferences: List<DietaryPreference> = emptyList(),
    val selectedServingOption: ServingOption = ServingOption.ANY,
    val includedIngredients: List<String> = emptyList(),
    val excludedIngredients: List<String> = emptyList()
)
