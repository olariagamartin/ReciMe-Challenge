package com.themarto.recimechallenge.core.data.database

import com.themarto.recimechallenge.core.data.model.Result
import com.themarto.recimechallenge.core.data.model.Recipe

interface RecipesLocalStorage {

    suspend fun getSavedRecipe(id: Int): Result<Recipe>

    suspend fun saveRecipe(recipe: Recipe)

    suspend fun deleteRecipe(id: Int)

    suspend fun getAllSavedRecipes(): Result<List<Recipe>>
}