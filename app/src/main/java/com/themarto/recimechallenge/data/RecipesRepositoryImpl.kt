package com.themarto.recimechallenge.data

import com.themarto.recimechallenge.data.model.ExploreFeed
import com.themarto.recimechallenge.data.model.Recipe
import com.themarto.recimechallenge.data.model.RecipeSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecipesRepositoryImpl(
    private val recipesApi: RecipesApi,
    private val recipesLocalStorage: RecipesLocalStorage
) : RecipesRepository {

    private val savedRecipes = MutableStateFlow<List<Recipe>>(emptyList())

    override suspend fun getExploreFeed(): Result<ExploreFeed> {
        val feedResult = recipesApi.getExploreFeed()
        if (feedResult.isError()) return feedResult

        val savedIds = loadSavedIds()
        val feed = feedResult.value
        return Result.Success(
            feed.copy(
                recipeOfTheDay = feed.recipeOfTheDay.withSavedFlag(savedIds),
                recommended = feed.recommended.map { it.withSavedFlag(savedIds) }
            )
        )
    }

    override suspend fun getRecipe(id: Int): Result<Recipe> {
        val localResult = recipesLocalStorage.getSavedRecipe(id)
        if (localResult.isSuccess()) {
            return Result.Success(localResult.value.copy(saved = true))
        }

        val remoteResult = recipesApi.getRecipe(id)
        if (remoteResult.isError()) return remoteResult

        return Result.Success(remoteResult.value.withSavedFlag(loadSavedIds()))
    }

    override suspend fun searchRecipes(search: RecipeSearch): Result<List<Recipe>> {
        val searchResult = recipesApi.searchRecipes(search)
        if (searchResult.isError()) return searchResult

        val savedIds = loadSavedIds()
        return Result.Success(searchResult.value.map { it.withSavedFlag(savedIds) })
    }

    override suspend fun saveRecipe(recipe: Recipe) {
        recipesLocalStorage.saveRecipe(recipe.copy(saved = true))
        refreshSavedRecipes()
    }

    override suspend fun deleteRecipe(id: Int) {
        recipesLocalStorage.deleteRecipe(id)
        refreshSavedRecipes()
    }

    override suspend fun getAllSavedRecipes(): Flow<List<Recipe>> {
        refreshSavedRecipes()
        return savedRecipes.asStateFlow()
    }

    private suspend fun refreshSavedRecipes() {
        val result = recipesLocalStorage.getAllSavedRecipes()
        if (result.isSuccess()) {
            savedRecipes.value = result.value.map { it.copy(saved = true) }
        }
    }

    private suspend fun loadSavedIds(): Set<String> {
        val result = recipesLocalStorage.getAllSavedRecipes()
        return if (result.isSuccess()) result.value.map { it.id }.toSet() else emptySet()
    }

    private fun Recipe.withSavedFlag(savedIds: Set<String>): Recipe {
        return copy(saved = id in savedIds)
    }
}
