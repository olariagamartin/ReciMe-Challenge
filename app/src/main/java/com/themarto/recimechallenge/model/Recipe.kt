package com.themarto.recimechallenge.model

data class Recipe(
    val id: String,
    val title: String,
    val imgUrl: String,
    val servings: Int,
    val rating: Rating,
    val timeInMinutes: Int,
    val dietaryAttributes: List<String>,
    val saved: Boolean
)

data class Rating(
    val stars: Double,
    val reviews: Int
)
