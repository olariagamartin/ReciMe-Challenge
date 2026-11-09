package com.themarto.recimechallenge.features.search.model

import com.themarto.recimechallenge.data.model.DietaryAttribute

data class DietaryItem(
    val dietaryAttribute: DietaryAttribute,
    val isSelected: Boolean = false
)