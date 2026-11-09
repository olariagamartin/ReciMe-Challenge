package com.themarto.recimechallenge.data

import com.themarto.recimechallenge.model.Recipe

interface RecipesRepository {

    suspend fun getExploreFeed(): Result<ExploreFeed>

    suspend fun getRecipe(id: Int): Result<Recipe>

}