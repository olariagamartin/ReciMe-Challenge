package com.themarto.recimechallenge.core.data.database

import com.themarto.recimechallenge.core.data.model.Recipe
import com.themarto.recimechallenge.core.data.model.Result

// simulates a Room implementation
class FakeRecipesStorage : RecipesLocalStorage {

    private val recipes = linkedMapOf<Int, Recipe>()

    override suspend fun getSavedRecipe(id: Int): Result<Recipe> {
        val recipe = recipes[id] ?: return Result.Error("Recipe $id not found")
        return Result.Success(recipe)
    }

    override suspend fun saveRecipe(recipe: Recipe) {
        val id = recipe.id.toIntOrNull() ?: return
        recipes[id] = recipe.copy(saved = true)
    }

    override suspend fun deleteRecipe(id: Int) {
        recipes.remove(id)
    }

    override suspend fun getAllSavedRecipes(): Result<List<Recipe>> {
        return Result.Success(recipes.values.toList())
    }
}
