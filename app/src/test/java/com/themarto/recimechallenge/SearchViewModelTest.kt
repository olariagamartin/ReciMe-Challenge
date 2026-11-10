package com.themarto.recimechallenge

import com.themarto.recimechallenge.core.data.model.DietaryAttribute
import com.themarto.recimechallenge.core.data.model.ExploreFeed
import com.themarto.recimechallenge.core.data.model.Recipe
import com.themarto.recimechallenge.core.data.model.RecipeSearch
import com.themarto.recimechallenge.core.data.model.Result
import com.themarto.recimechallenge.core.data.repository.RecipesRepository
import com.themarto.recimechallenge.features.search.model.FiltersStatus
import com.themarto.recimechallenge.features.search.model.SearchScreenParam
import com.themarto.recimechallenge.features.search.model.ServingOption
import com.themarto.recimechallenge.features.search.presentation.SearchUiState
import com.themarto.recimechallenge.features.search.presentation.SearchViewModel
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

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
    fun `0-WHEN opened from search bar THEN search field is focused`() = runTest {
        val viewModel = SearchViewModel(FakeRecipesRepository(), SearchScreenParam.SearchBar)

        assertEquals(SearchUiState(focusSearch = true), viewModel.uiState.value)
    }

    @Test
    fun `1-WHEN opened with no request THEN UiState is empty`() = runTest {
        val viewModel = SearchViewModel(FakeRecipesRepository(), SearchScreenParam.None)

        assertEquals(SearchUiState(), viewModel.uiState.value)
    }

    @Test
    fun `2-WHEN opened with a dietary filter and search succeeds THEN filter is applied and results are shown`() =
        runTest {
            val recipes = PreviewData.recipeList
            val repository = FakeRecipesRepository(searchResults = flowOf(Result.Success(recipes)))
            val viewModel = SearchViewModel(
                repository,
                SearchScreenParam.Filter(DietaryAttribute.VEGAN)
            )

            assertEquals(
                SearchUiState(
                    filtersStatus = FiltersStatus(
                        dietaryAttrsSelected = listOf(DietaryAttribute.VEGAN)
                    ),
                    filtersApplied = 1,
                    results = recipes
                ),
                viewModel.uiState.value
            )
            assertEquals(
                listOf(RecipeSearch(dietaryPreferences = listOf(DietaryAttribute.VEGAN))),
                repository.searches
            )
        }

    @Test
    fun `3-WHEN opened with a dietary filter and search fails THEN UiState has the error`() = runTest {
        val viewModel = SearchViewModel(
            FakeRecipesRepository(searchResults = flowOf(Result.Error("Network error"))),
            SearchScreenParam.Filter(DietaryAttribute.KETO)
        )

        assertEquals(
            SearchUiState(
                filtersStatus = FiltersStatus(
                    dietaryAttrsSelected = listOf(DietaryAttribute.KETO)
                ),
                filtersApplied = 1,
                error = "Network error"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `4-WHEN query changes THEN search query is updated`() = runTest {
        val viewModel = SearchViewModel(FakeRecipesRepository(), SearchScreenParam.None)

        viewModel.onQueryChange("pasta")

        assertEquals("pasta", viewModel.uiState.value.searchQuery)
    }

    @Test
    fun `5-WHEN search succeeds THEN results replace the loading state`() = runTest {
        val recipes = PreviewData.recipeList
        val repository = FakeRecipesRepository(searchResults = flowOf(Result.Success(recipes)))
        val viewModel = SearchViewModel(repository, SearchScreenParam.None)
        viewModel.onQueryChange("pasta")

        viewModel.onSearch()

        assertEquals(
            SearchUiState(
                searchQuery = "pasta",
                results = recipes
            ),
            viewModel.uiState.value
        )
        assertEquals(listOf(RecipeSearch(query = "pasta")), repository.searches)
    }

    @Test
    fun `6-WHEN search fails THEN UiState has the error`() = runTest {
        val viewModel = SearchViewModel(
            FakeRecipesRepository(searchResults = flowOf(Result.Error("Network error"))),
            SearchScreenParam.None
        )
        viewModel.onQueryChange("pasta")

        viewModel.onSearch()

        assertEquals(
            SearchUiState(
                searchQuery = "pasta",
                error = "Network error"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `7-WHEN filter button is clicked THEN filters panel toggles`() = runTest {
        val viewModel = SearchViewModel(FakeRecipesRepository(), SearchScreenParam.None)

        viewModel.onFilterClick()
        assertTrue(viewModel.uiState.value.filtersOpen)

        viewModel.onFilterClick()
        assertFalse(viewModel.uiState.value.filtersOpen)
    }

    @Test
    fun `8-WHEN dietary preferences are selected and removed THEN applied filter count updates`() = runTest {
        val viewModel = SearchViewModel(FakeRecipesRepository(), SearchScreenParam.None)

        viewModel.onDietaryPreferenceSelected(DietaryAttribute.VEGAN)
        viewModel.onDietaryPreferenceSelected(DietaryAttribute.GLUTEN_FREE)

        assertEquals(
            listOf(DietaryAttribute.VEGAN, DietaryAttribute.GLUTEN_FREE),
            viewModel.uiState.value.filtersStatus.dietaryAttrsSelected
        )
        assertEquals(2, viewModel.uiState.value.filtersApplied)

        viewModel.onDietaryPreferenceSelected(DietaryAttribute.VEGAN)

        assertEquals(
            listOf(DietaryAttribute.GLUTEN_FREE),
            viewModel.uiState.value.filtersStatus.dietaryAttrsSelected
        )
        assertEquals(1, viewModel.uiState.value.filtersApplied)
    }

    @Test
    fun `9-WHEN a serving option is selected THEN it counts as an applied filter`() = runTest {
        val viewModel = SearchViewModel(FakeRecipesRepository(), SearchScreenParam.None)

        viewModel.onServingOptionSelected(ServingOption.ONE_TWO)

        assertEquals(ServingOption.ONE_TWO, viewModel.uiState.value.filtersStatus.selectedServingOption)
        assertEquals(1, viewModel.uiState.value.filtersApplied)

        viewModel.onServingOptionSelected(ServingOption.ANY)

        assertEquals(ServingOption.ANY, viewModel.uiState.value.filtersStatus.selectedServingOption)
        assertEquals(0, viewModel.uiState.value.filtersApplied)
    }

    @Test
    fun `10-WHEN ingredients are included and excluded THEN applied filter count updates`() = runTest {
        val viewModel = SearchViewModel(FakeRecipesRepository(), SearchScreenParam.None)

        viewModel.onAddToIncluded("tomato")
        viewModel.onAddToExcluded("milk")

        assertEquals(listOf("tomato"), viewModel.uiState.value.filtersStatus.includedIngredients)
        assertEquals(listOf("milk"), viewModel.uiState.value.filtersStatus.excludedIngredients)
        assertEquals(2, viewModel.uiState.value.filtersApplied)

        viewModel.onRemoveFromIncluded("tomato")
        viewModel.onRemoveFromExcluded("milk")

        assertTrue(viewModel.uiState.value.filtersStatus.includedIngredients.isEmpty())
        assertTrue(viewModel.uiState.value.filtersStatus.excludedIngredients.isEmpty())
        assertEquals(0, viewModel.uiState.value.filtersApplied)
    }

    @Test
    fun `11-WHEN filters are reset THEN filter status is cleared`() = runTest {
        val viewModel = SearchViewModel(FakeRecipesRepository(), SearchScreenParam.None)
        viewModel.onDietaryPreferenceSelected(DietaryAttribute.VEGAN)
        viewModel.onServingOptionSelected(ServingOption.FIVE_PLUS)
        viewModel.onAddToIncluded("tomato")
        viewModel.onAddToExcluded("milk")

        viewModel.onResetFilters()

        assertEquals(FiltersStatus(), viewModel.uiState.value.filtersStatus)
        assertEquals(0, viewModel.uiState.value.filtersApplied)
    }

    @Test
    fun `12-WHEN filters are applied and search succeeds THEN panel closes and results are shown`() = runTest {
        val recipes = PreviewData.recipeList
        val repository = FakeRecipesRepository(searchResults = flowOf(Result.Success(recipes)))
        val viewModel = SearchViewModel(repository, SearchScreenParam.None)
        viewModel.onQueryChange("salad")
        viewModel.onFilterClick()
        viewModel.onDietaryPreferenceSelected(DietaryAttribute.VEGETARIAN)
        viewModel.onServingOptionSelected(ServingOption.THREE_FOUR)
        viewModel.onAddToIncluded("tomato")
        viewModel.onAddToExcluded("milk")

        viewModel.onApplyFilters()

        assertEquals(
            SearchUiState(
                searchQuery = "salad",
                filtersApplied = 4,
                filtersOpen = false,
                filtersStatus = FiltersStatus(
                    dietaryAttrsSelected = listOf(DietaryAttribute.VEGETARIAN),
                    selectedServingOption = ServingOption.THREE_FOUR,
                    includedIngredients = listOf("tomato"),
                    excludedIngredients = listOf("milk")
                ),
                results = recipes
            ),
            viewModel.uiState.value
        )
        assertEquals(
            listOf(
                RecipeSearch(
                    query = "salad",
                    dietaryPreferences = listOf(DietaryAttribute.VEGETARIAN),
                    servingOption = ServingOption.THREE_FOUR,
                    includeIngredients = listOf("tomato"),
                    excludeIngredients = listOf("milk")
                )
            ),
            repository.searches
        )
    }

    @Test
    fun `13-WHEN unsaved recipe is bookmarked THEN recipe is saved`() = runTest {
        val repository = FakeRecipesRepository()
        val viewModel = SearchViewModel(repository, SearchScreenParam.None)
        val recipe = PreviewData.recipe.copy(saved = false)

        viewModel.onBookmarkClick(recipe)

        assertEquals(listOf(recipe), repository.savedRecipes)
        assertTrue(repository.deletedRecipeIds.isEmpty())
    }

    @Test
    fun `14-WHEN saved recipe is bookmarked THEN recipe is deleted`() = runTest {
        val repository = FakeRecipesRepository()
        val viewModel = SearchViewModel(repository, SearchScreenParam.None)
        val recipe = PreviewData.recipe.copy(saved = true)

        viewModel.onBookmarkClick(recipe)

        assertEquals(listOf(recipe.id.toInt()), repository.deletedRecipeIds)
        assertTrue(repository.savedRecipes.isEmpty())
    }

    @Test
    fun `15-WHEN search has not returned THEN loading stays true and error stays empty`() = runTest {
        val viewModel = SearchViewModel(FakeRecipesRepository(), SearchScreenParam.None)
        viewModel.onQueryChange("pasta")

        viewModel.onSearch()

        assertTrue(viewModel.uiState.value.loading)
        assertNull(viewModel.uiState.value.error)
        assertTrue(viewModel.uiState.value.results.isEmpty())
    }

    private class FakeRecipesRepository(
        private val searchResults: Flow<Result<List<Recipe>>> = emptyFlow()
    ) : RecipesRepository {

        val savedRecipes = mutableListOf<Recipe>()
        val deletedRecipeIds = mutableListOf<Int>()
        val searches = mutableListOf<RecipeSearch>()

        override fun getExploreFeed(): Flow<Result<ExploreFeed>> = emptyFlow()

        override fun getRecipe(id: Int): Flow<Result<Recipe>> = emptyFlow()

        override fun searchRecipes(search: RecipeSearch): Flow<Result<List<Recipe>>> {
            searches += search
            return searchResults
        }

        override suspend fun saveRecipe(recipe: Recipe) {
            savedRecipes += recipe
        }

        override suspend fun deleteRecipe(id: Int) {
            deletedRecipeIds += id
        }

        override fun getAllSavedRecipes(): Flow<List<Recipe>> = emptyFlow()
    }
}
