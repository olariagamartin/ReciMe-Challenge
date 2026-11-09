package com.themarto.recimechallenge.data.repository

import com.themarto.recimechallenge.data.model.Result
import com.themarto.recimechallenge.data.database.RecipesLocalStorage
import com.themarto.recimechallenge.data.model.isError
import com.themarto.recimechallenge.data.model.isSuccess
import com.themarto.recimechallenge.data.model.ExploreFeed
import com.themarto.recimechallenge.data.model.Recipe
import com.themarto.recimechallenge.data.model.RecipeSearch
import com.themarto.recimechallenge.data.network.RecipesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class RecipesRepositoryImpl(
    private val recipesApi: RecipesApi,
    private val recipesLocalStorage: RecipesLocalStorage
) : RecipesRepository {

    private val savedRecipes = MutableStateFlow<List<Recipe>>(emptyList())

    override fun getExploreFeed(): Flow<Result<ExploreFeed>> = flow {
        // simulates network delay
        delay(200)
        val feedResult = recipesApi.getExploreFeed()
        if (feedResult.isError()) {
            emit(feedResult)
            return@flow
        }

        val feed = feedResult.value
        emitAll(
            observeSavedIds().map { savedIds ->
                Result.Success(
                    feed.copy(
                        recipeOfTheDay = feed.recipeOfTheDay.withSavedFlag(savedIds),
                        recommended = feed.recommended.map { it.withSavedFlag(savedIds) }
                    )
                )
            }
        )
    }

    override fun getRecipe(id: Int): Flow<Result<Recipe>> = flow {
        val remoteResult = recipesApi.getRecipe(id)
        if (remoteResult.isError()) {
            emit(remoteResult)
            return@flow
        }

        val recipe = remoteResult.value
        emitAll(
            observeSavedIds().map { savedIds ->
                Result.Success(recipe.withSavedFlag(savedIds))
            }
        )
    }

    override fun searchRecipes(search: RecipeSearch): Flow<Result<List<Recipe>>> = flow {
        delay(400) // simulates network delay
        val searchResult = recipesApi.searchRecipes(search)
        if (searchResult.isError()) {
            emit(searchResult)
            return@flow
        }

        val recipes = searchResult.value
        emitAll(
            observeSavedIds().map { savedIds ->
                Result.Success(recipes.map { it.withSavedFlag(savedIds) })
            }
        )
    }

    override suspend fun saveRecipe(recipe: Recipe) {
        recipesLocalStorage.saveRecipe(recipe.copy(saved = true))
        refreshSavedRecipes()
    }

    override suspend fun deleteRecipe(id: Int) {
        recipesLocalStorage.deleteRecipe(id)
        refreshSavedRecipes()
    }

    override fun getAllSavedRecipes(): Flow<List<Recipe>> {
        return savedRecipes.asStateFlow()
    }

    private suspend fun refreshSavedRecipes() {
        val result = recipesLocalStorage.getAllSavedRecipes()
        if (result.isSuccess()) {
            savedRecipes.value = result.value.map { it.copy(saved = true) }
        }
    }

    private fun observeSavedIds(): Flow<Set<String>> {
        return savedRecipes
            .onStart { refreshSavedRecipes() }
            .map { recipes -> recipes.map { it.id }.toSet() }
    }

    private fun Recipe.withSavedFlag(savedIds: Set<String>): Recipe {
        return copy(saved = id in savedIds)
    }
}