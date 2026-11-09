package com.themarto.recimechallenge

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.themarto.recimechallenge.components.BottomNavBar
import com.themarto.recimechallenge.features.explore.ExploreDestination
import com.themarto.recimechallenge.features.explore.presentation.ExploreScreen
import com.themarto.recimechallenge.features.recipe_details.RecipeDetailsDestination
import com.themarto.recimechallenge.features.recipe_details.presentation.RecipeDetailsScreen
import com.themarto.recimechallenge.features.saved.SavedDestination
import com.themarto.recimechallenge.features.saved.SavedScreen
import com.themarto.recimechallenge.features.search.SearchDestinations
import com.themarto.recimechallenge.features.search.model.SearchScreenParam
import com.themarto.recimechallenge.features.search.presentation.SearchScreen

@Composable
fun RecipeApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showBottomBar = currentRoute in listOf(
        ExploreDestination.route,
        SavedDestination.route
    ) || currentRoute?.startsWith(SearchDestinations.Search.route) == true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onTabSelected = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        RecipeNavHost(
            navController = navController,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun RecipeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ExploreDestination.route,
        modifier = modifier
    ) {
        composable(ExploreDestination.route) {
            ExploreScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(RecipeDetailsDestination.createRoute(recipeId))
                },
                onSearchClick = {
                    navController.navigate(SearchDestinations.SearchWithParams.withSearchBar()) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                onFilterClick = { dietaryAttribute ->
                    navController.navigate(SearchDestinations.SearchWithParams.withFilter(dietaryAttribute)) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                }
            )
        }
        composable(SearchDestinations.SearchWithParams.route) { backStackEntry ->
            SearchScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(RecipeDetailsDestination.createRoute(recipeId))
                },
                params = SearchScreenParam.SearchBar
            )
        }

        composable(SearchDestinations.Search.route) {
            SearchScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(RecipeDetailsDestination.createRoute(recipeId))
                },
                params = SearchScreenParam.None
            )
        }
        composable(SavedDestination.route) {
            SavedScreen()
        }
        composable(RecipeDetailsDestination.route) {
            RecipeDetailsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}