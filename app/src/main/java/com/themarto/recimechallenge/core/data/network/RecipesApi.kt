package com.themarto.recimechallenge.core.data.network

import com.themarto.recimechallenge.core.data.model.Result
import com.themarto.recimechallenge.core.data.model.ExploreFeed
import com.themarto.recimechallenge.core.data.model.Recipe
import com.themarto.recimechallenge.core.data.model.RecipeSearch

interface RecipesApi {

    suspend fun getExploreFeed(): Result<ExploreFeed>

    suspend fun searchRecipes(search: RecipeSearch): Result<List<Recipe>>

    suspend fun getRecipe(id: Int): Result<Recipe>
}