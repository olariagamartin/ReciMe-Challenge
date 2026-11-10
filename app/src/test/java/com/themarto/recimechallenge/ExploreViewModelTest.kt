package com.themarto.recimechallenge

import com.themarto.recimechallenge.core.data.model.ExploreFeed
import com.themarto.recimechallenge.core.data.model.Recipe
import com.themarto.recimechallenge.core.data.model.RecipeSearch
import com.themarto.recimechallenge.core.data.model.Result
import com.themarto.recimechallenge.core.data.repository.RecipesRepository
import com.themarto.recimechallenge.features.explore.presentation.ExploreUiState
import com.themarto.recimechallenge.features.explore.presentation.ExploreViewModel
import com.themarto.recimechallenge.core.ui.preview.PreviewData
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
class ExploreViewModelTest {

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
        val viewModel = ExploreViewModel(FakeRecipesRepository())

        assertEquals(ExploreUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `1-WHEN explore feed succeeds THEN UiState is Success`() = runTest {
        val feed = ExploreFeed(
            recipeOfTheDay = PreviewData.recipe,
            recommended = PreviewData.recipeList
        )
        val viewModel = ExploreViewModel(
            FakeRecipesRepository(exploreFeed = flowOf(Result.Success(feed)))
        )

        val state = viewModel.uiState.first { it is ExploreUiState.Success }

        assertEquals(
            ExploreUiState.Success(
                recipes = feed.recommended,
                recipeOfTheDay = feed.recipeOfTheDay
            ),
            state
        )
    }

    @Test
    fun `2-WHEN explore feed fails THEN UiState is Error`() = runTest {
        val viewModel = ExploreViewModel(
            FakeRecipesRepository(exploreFeed = flowOf(Result.Error("Network error")))
        )

        val state = viewModel.uiState.first { it is ExploreUiState.Error }

        assertEquals(ExploreUiState.Error("Network error"), state)
    }

    @Test
    fun `3-WHEN unsaved recipe is bookmarked THEN recipe is saved`() = runTest {
        val repository = FakeRecipesRepository()
        val viewModel = ExploreViewModel(repository)
        val recipe = PreviewData.recipe.copy(saved = false)

        viewModel.onBookmarkClick(recipe)

        assertEquals(listOf(recipe), repository.savedRecipes)
        assertTrue(repository.deletedRecipeIds.isEmpty())
    }

    @Test
    fun `4-WHEN saved recipe is bookmarked THEN recipe is deleted`() = runTest {
        val repository = FakeRecipesRepository()
        val viewModel = ExploreViewModel(repository)
        val recipe = PreviewData.recipe.copy(saved = true)

        viewModel.onBookmarkClick(recipe)

        assertEquals(listOf(recipe.id.toInt()), repository.deletedRecipeIds)
        assertTrue(repository.savedRecipes.isEmpty())
    }

    private class FakeRecipesRepository(
        private val exploreFeed: Flow<Result<ExploreFeed>> = emptyFlow()
    ) : RecipesRepository {

        val savedRecipes = mutableListOf<Recipe>()
        val deletedRecipeIds = mutableListOf<Int>()

        override fun getExploreFeed(): Flow<Result<ExploreFeed>> = exploreFeed

        override fun getRecipe(id: Int): Flow<Result<Recipe>> = emptyFlow()

        override fun searchRecipes(search: RecipeSearch): Flow<Result<List<Recipe>>> = emptyFlow()

        override suspend fun saveRecipe(recipe: Recipe) {
            savedRecipes += recipe
        }

        override suspend fun deleteRecipe(id: Int) {
            deletedRecipeIds += id
        }

        override fun getAllSavedRecipes(): Flow<List<Recipe>> = emptyFlow()
    }
}
