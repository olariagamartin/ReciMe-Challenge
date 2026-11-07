package com.themarto.recimechallenge.navigation

sealed class Destinations(val route: String) {
    object Explore : Destinations("explore")
    object Search : Destinations("search")
    object Saved : Destinations("saved")
    object RecipeDetails : Destinations("recipeDetails/{recipeId}") {
        fun createRoute(recipeId: String) = "recipeDetails/$recipeId"
    }
}