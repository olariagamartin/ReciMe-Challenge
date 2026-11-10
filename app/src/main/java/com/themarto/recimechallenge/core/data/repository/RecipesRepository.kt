package com.themarto.recimechallenge.core.data.repository

import com.themarto.recimechallenge.core.data.model.Result
import com.themarto.recimechallenge.core.data.model.ExploreFeed
import com.themarto.recimechallenge.core.data.model.Recipe
import com.themarto.recimechallenge.core.data.model.RecipeSearch
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    fun getExploreFeed(): Flow<Result<ExploreFeed>>

    fun getRecipe(id: Int): Flow<Result<Recipe>>

    fun searchRecipes(search: RecipeSearch): Flow<Result<List<Recipe>>>

    suspend fun saveRecipe(recipe: Recipe)

    suspend fun deleteRecipe(id: Int)

    fun getAllSavedRecipes(): Flow<List<Recipe>>

}