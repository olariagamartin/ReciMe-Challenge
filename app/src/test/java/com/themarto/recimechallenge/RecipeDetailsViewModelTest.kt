package com.themarto.recimechallenge

import com.themarto.recimechallenge.core.data.model.ExploreFeed
import com.themarto.recimechallenge.core.data.model.Recipe
import com.themarto.recimechallenge.core.data.model.RecipeSearch
import com.themarto.recimechallenge.core.data.model.Result
import com.themarto.recimechallenge.core.data.repository.RecipesRepository
import com.themarto.recimechallenge.features.recipe_details.presentation.RecipeDetailsUiState
import com.themarto.recimechallenge.features.recipe_details.presentation.RecipeDetailsViewModel
import com.themarto.recimechallenge.core.ui.preview.PreviewData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
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
class RecipeDetailsViewModelTest {

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
        val viewModel = RecipeDetailsViewModel(
            recipeId = PreviewData.recipe.id,
            recipesRepository = FakeRecipesRepository()
        )

        assertEquals(RecipeDetailsUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `1-WHEN recipe succeeds THEN UiState is Success`() = runTest {
        val recipe = PreviewData.recipe
        val repository = FakeRecipesRepository(recipeResult = flowOf(Result.Success(recipe)))
        val viewModel = RecipeDetailsViewModel(recipe.id, repository)

        assertEquals(RecipeDetailsUiState.Success(recipe = recipe), viewModel.uiState.value)
        assertEquals(listOf(recipe.id.toInt()), repository.requestedRecipeIds)
    }

    @Test
    fun `2-WHEN recipe fails THEN UiState is Error`() = runTest {
        val viewModel = RecipeDetailsViewModel(
            recipeId = PreviewData.recipe.id,
            recipesRepository = FakeRecipesRepository(recipeResult = flowOf(Result.Error("Network error")))
        )

        assertEquals(RecipeDetailsUiState.Error("Network error"), viewModel.uiState.value)
    }

    @Test
    fun `3-WHEN unsaved recipe is bookmarked THEN recipe is saved`() = runTest {
        val recipe = PreviewData.recipe.copy(saved = false)
        val repository = FakeRecipesRepository(recipeResult = flowOf(Result.Success(recipe)))
        val viewModel = RecipeDetailsViewModel(recipe.id, repository)

        viewModel.onBookmarkClick()

        assertEquals(listOf(recipe), repository.savedRecipes)
        assertTrue(repository.deletedRecipeIds.isEmpty())
    }

    @Test
    fun `4-WHEN saved recipe is bookmarked THEN recipe is deleted`() = runTest {
        val recipe = PreviewData.recipe.copy(saved = true)
        val repository = FakeRecipesRepository(recipeResult = flowOf(Result.Success(recipe)))
        val viewModel = RecipeDetailsViewModel(recipe.id, repository)

        viewModel.onBookmarkClick()

        assertEquals(listOf(recipe.id.toInt()), repository.deletedRecipeIds)
        assertTrue(repository.savedRecipes.isEmpty())
    }

    @Test
    fun `5-WHEN bookmark is clicked before recipe loads THEN repository is unchanged`() = runTest {
        val repository = FakeRecipesRepository()
        val viewModel = RecipeDetailsViewModel(PreviewData.recipe.id, repository)

        viewModel.onBookmarkClick()

        assertTrue(repository.savedRecipes.isEmpty())
        assertTrue(repository.deletedRecipeIds.isEmpty())
    }

    @Test
    fun `6-WHEN ingredient is checked and unchecked THEN checked ingredients update`() = runTest {
        val recipe = PreviewData.recipe
        val viewModel = RecipeDetailsViewModel(
            recipe.id,
            FakeRecipesRepository(recipeResult = flowOf(Result.Success(recipe)))
        )

        viewModel.onIngredientCheckedChange(0)
        viewModel.onIngredientCheckedChange(2)

        assertEquals(
            RecipeDetailsUiState.Success(
                recipe = recipe,
                checkedIngredients = setOf(0, 2)
            ),
            viewModel.uiState.value
        )

        viewModel.onIngredientCheckedChange(0)

        assertEquals(
            RecipeDetailsUiState.Success(
                recipe = recipe,
                checkedIngredients = setOf(2)
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `7-WHEN ingredient is toggled before recipe loads THEN UiState stays loading`() = runTest {
        val viewModel = RecipeDetailsViewModel(
            PreviewData.recipe.id,
            FakeRecipesRepository()
        )

        viewModel.onIngredientCheckedChange(0)

        assertEquals(RecipeDetailsUiState.Loading, viewModel.uiState.value)
    }

    private class FakeRecipesRepository(
        private val recipeResult: Flow<Result<Recipe>> = emptyFlow()
    ) : RecipesRepository {

        val savedRecipes = mutableListOf<Recipe>()
        val deletedRecipeIds = mutableListOf<Int>()
        val requestedRecipeIds = mutableListOf<Int>()

        override fun getExploreFeed(): Flow<Result<ExploreFeed>> = emptyFlow()

        override fun getRecipe(id: Int): Flow<Result<Recipe>> {
            requestedRecipeIds += id
            return recipeResult
        }

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
