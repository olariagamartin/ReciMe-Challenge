package com.themarto.recimechallenge.ui.preview

import com.themarto.recimechallenge.model.Recipe
import com.themarto.recimechallenge.model.Rating
import com.themarto.recimechallenge.model.Ingredient

object PreviewData {
    val ingredients = listOf(
        Ingredient("Tomato", "2", "pcs"),
        Ingredient("Onion", "1", "pc"),
        Ingredient("Garlic", "3", "cloves"),
        Ingredient("Olive Oil", "2", "tbsp")
    )

    val recipe = Recipe(
        id = "1",
        title = "Roasted Vegetables",
        imgUrl = "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=1200&q=80",
        servings = 1,
        rating = Rating(stars = 1.0, reviews = 1),
        timeInMinutes = 1,
        dietaryAttributes = listOf("🌿 Vegetarian", "🌾 Gluten-Free", "🥛 Dairy-Free"),
        saved = true,
        ingredients = ingredients,
        instructions = listOf(
            "Preheat the oven to 200°C (400°F).",
            "In a large bowl, toss the <b>potatoes</b> with olive oil, salt, and pepper.",
            "Spread the potatoes in a single layer on a baking sheet.",
            "Roast for 25-30 minutes, or until golden brown and crispy.",
            "Serve hot and enjoy!"
        )
    )

    val recipeList = listOf(
        recipe,
        recipe.copy(id = "2", title = "Roasted Vegetables 2"),
        recipe.copy(id = "3", title = "Roasted Vegetables 3"),
        recipe.copy(id = "4", title = "Roasted Vegetables 4"),
    )
}