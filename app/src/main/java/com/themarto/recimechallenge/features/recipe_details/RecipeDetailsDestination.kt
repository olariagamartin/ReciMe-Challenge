package com.themarto.recimechallenge.features.recipe_details

import com.themarto.recimechallenge.core.navigation.Destination

object RecipeDetailsDestination : Destination {
    override val route: String = "details/{$PARAM_RECIPE_ID}"
    const val PARAM_RECIPE_ID = "recipeId"

    fun createRoute(recipeId: String) = "details/$recipeId"
}