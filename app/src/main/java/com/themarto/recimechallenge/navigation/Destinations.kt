package com.themarto.recimechallenge.navigation

import com.themarto.recimechallenge.data.model.DietaryAttribute

sealed class Destinations(val route: String) {
    object Explore : Destinations("explore")
    object Search : Destinations("search")
    object SearchWithParams : Destinations("search/{openWith}") {
        fun withFilter(filter: DietaryAttribute) = "search/${filter.name}"
        fun withSearchBar() = "search/searchBar"
    }
    object Saved : Destinations("saved")
    object RecipeDetails : Destinations("recipeDetails/{recipeId}") {
        fun createRoute(recipeId: String) = "recipeDetails/$recipeId"
    }
}