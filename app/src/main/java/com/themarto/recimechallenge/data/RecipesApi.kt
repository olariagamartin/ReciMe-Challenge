package com.themarto.recimechallenge.data

interface RecipesApi {

    suspend fun getExploreFeed(): Result<ExploreFeed>

    suspend fun searchRecipes(search: RecipeSearch): Result<List<Recipe>>

    suspend fun getRecipe(id: Int): Result<Recipe>
}