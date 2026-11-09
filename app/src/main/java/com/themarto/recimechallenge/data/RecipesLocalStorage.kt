package com.themarto.recimechallenge.data

interface RecipesLocalStorage {

    suspend fun getSavedRecipe(id: Int): Result<Recipe>

    suspend fun saveRecipe(recipe: Recipe)

    suspend fun deleteRecipe(id: Int)

    suspend fun getAllSavedRecipes(): Result<List<Recipe>>
}