package com.themarto.recimechallenge

import com.themarto.recimechallenge.data.model.ExploreFeed
import com.themarto.recimechallenge.data.model.Recipe
import com.themarto.recimechallenge.data.model.RecipeSearch
import com.themarto.recimechallenge.data.model.Result
import com.themarto.recimechallenge.data.repository.RecipesRepository
import com.themarto.recimechallenge.features.saved.presentation.SavedUiState
import com.themarto.recimechallenge.features.saved.presentation.SavedViewModel
import com.themarto.recimechallenge.ui.preview.PreviewData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SavedViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `0-WHEN ViewModel is created THEN UiState is loading`() = runTest {
        val viewModel = SavedViewModel(FakeRecipesRepository())

        assertEquals(SavedUiState(loading = true), viewModel.uiState.value)
    }

    @Test
    fun `1-WHEN saved recipes are loaded THEN UiState shows them`() = runTest {
        val recipes = PreviewData.recipeList
        val viewModel = SavedViewModel(
            FakeRecipesRepository(savedRecipesFlow = flowOf(recipes))
        )

        val state = viewModel.uiState.first { !it.loading }

        assertEquals(
            SavedUiState(
                recipes = recipes,
                loading = false
            ),
            state
        )
    }

    @Test
    fun `2-WHEN there are no saved recipes THEN UiState is an empty list`() = runTest {
        val viewModel = SavedViewModel(
            FakeRecipesRepository(savedRecipesFlow = flowOf(emptyList()))
        )

        val state = viewModel.uiState.first { !it.loading }

        assertEquals(SavedUiState(recipes = emptyList(), loading = false), state)
    }

    @Test
    fun `3-WHEN unsaved recipe is bookmarked THEN recipe is saved`() = runTest {
        val repository = FakeRecipesRepository()
        val viewModel = SavedViewModel(repository)
        val recipe = PreviewData.recipe.copy(saved = false)

        viewModel.onBookmarkClick(recipe)

        assertEquals(listOf(recipe), repository.savedRecipes)
        assertTrue(repository.deletedRecipeIds.isEmpty())
    }

    @Test
    fun `4-WHEN saved recipe is bookmarked THEN recipe is deleted`() = runTest {
        val repository = FakeRecipesRepository()
        val viewModel = SavedViewModel(repository)
        val recipe = PreviewData.recipe.copy(saved = true)

        viewModel.onBookmarkClick(recipe)

        assertEquals(listOf(recipe.id.toInt()), repository.deletedRecipeIds)
        assertTrue(repository.savedRecipes.isEmpty())
    }

    private class FakeRecipesRepository(
        private val savedRecipesFlow: Flow<List<Recipe>> = emptyFlow()
    ) : RecipesRepository {

        val savedRecipes = mutableListOf<Recipe>()
        val deletedRecipeIds = mutableListOf<Int>()

        override fun getExploreFeed(): Flow<Result<ExploreFeed>> = emptyFlow()

        override fun getRecipe(id: Int): Flow<Result<Recipe>> = emptyFlow()

        override fun searchRecipes(search: RecipeSearch): Flow<Result<List<Recipe>>> = emptyFlow()

        override suspend fun saveRecipe(recipe: Recipe) {
            savedRecipes += recipe
        }

        override suspend fun deleteRecipe(id: Int) {
            deletedRecipeIds += id
        }

        override fun getAllSavedRecipes(): Flow<List<Recipe>> = savedRecipesFlow
    }
}
