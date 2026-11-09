package com.themarto.recimechallenge.data.database

import com.themarto.recimechallenge.data.model.Result
import com.themarto.recimechallenge.data.model.Recipe

interface RecipesLocalStorage {

    suspend fun getSavedRecipe(id: Int): Result<Recipe>

    suspend fun saveRecipe(recipe: Recipe)

    suspend fun deleteRecipe(id: Int)

    suspend fun getAllSavedRecipes(): Result<List<Recipe>>
}