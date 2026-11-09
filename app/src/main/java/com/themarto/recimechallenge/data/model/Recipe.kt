package com.themarto.recimechallenge.data.model

data class Recipe(
    val id: String,
    val title: String,
    val imgUrl: String,
    val servings: Int,
    val rating: Rating,
    val timeInMinutes: Int,
    val dietaryAttributes: List<String>,
    val saved: Boolean,
    val ingredients: List<Ingredient>,
    val instructions: List<String>
)

data class Rating(
    val stars: Float,
    val reviews: Int
)

data class Ingredient(
    val name: String,
    val amount: String,
    val unit: String
)
