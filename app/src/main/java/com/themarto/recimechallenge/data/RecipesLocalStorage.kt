package com.themarto.recimechallenge.data

import com.themarto.recimechallenge.model.Recipe

interface RecipesLocalStorage {

    suspend fun getSavedRecipe(id: Int): Result<Recipe>

    suspend fun saveRecipe(recipe: Recipe)

    suspend fun deleteRecipe(id: Int)

    suspend fun getAllSavedRecipes(): Result<List<Recipe>>
}