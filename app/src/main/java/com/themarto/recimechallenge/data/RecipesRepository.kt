package com.themarto.recimechallenge.data

import com.themarto.recimechallenge.data.model.ExploreFeed
import com.themarto.recimechallenge.data.model.Recipe
import com.themarto.recimechallenge.data.model.RecipeSearch
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    suspend fun getExploreFeed(): Result<ExploreFeed>

    suspend fun getRecipe(id: Int): Result<Recipe>

    suspend fun searchRecipes(search: RecipeSearch): Result<List<Recipe>>

    suspend fun saveRecipe(recipe: Recipe)

    suspend fun deleteRecipe(id: Int)

    suspend fun getAllSavedRecipes(): Flow<List<Recipe>>

}