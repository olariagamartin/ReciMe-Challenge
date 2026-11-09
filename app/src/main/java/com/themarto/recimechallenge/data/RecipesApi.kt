package com.themarto.recimechallenge.data

import com.themarto.recimechallenge.data.model.ExploreFeed
import com.themarto.recimechallenge.data.model.Recipe
import com.themarto.recimechallenge.data.model.RecipeSearch

interface RecipesApi {

    suspend fun getExploreFeed(): Result<ExploreFeed>

    suspend fun searchRecipes(search: RecipeSearch): Result<List<Recipe>>

    suspend fun getRecipe(id: Int): Result<Recipe>
}