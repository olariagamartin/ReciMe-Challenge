package com.themarto.recimechallenge.ui.preview

import com.themarto.recimechallenge.model.Recipe
import com.themarto.recimechallenge.model.Rating

object PreviewData {
    val recipe = Recipe(
        id = "1",
        title = "Roasted Vegetables",
        imgUrl = "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=1200&q=80",
        servings = 1,
        rating = Rating(stars = 1.0, reviews = 1),
        timeInMinutes = 1,
        dietaryAttributes = listOf("vegetarian", "gluten-free", "dairy-free"),
        saved = true
    )
}