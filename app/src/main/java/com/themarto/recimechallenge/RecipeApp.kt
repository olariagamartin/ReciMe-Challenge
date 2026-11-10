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
import com.themarto.recimechallenge.core.components.BottomNavBar
import com.themarto.recimechallenge.features.explore.ExploreDestination
import com.themarto.recimechallenge.features.explore.presentation.ExploreScreen
import com.themarto.recimechallenge.features.recipe_details.RecipeDetailsDestination
import com.themarto.recimechallenge.features.recipe_details.presentation.RecipeDetailsScreen
import com.themarto.recimechallenge.features.saved.SavedDestination
import com.themarto.recimechallenge.features.saved.SavedScreen
import com.themarto.recimechallenge.features.search.SearchDestination
import com.themarto.recimechallenge.features.search.model.getSearchScreenParam
import com.themarto.recimechallenge.features.search.presentation.SearchScreen

@Composable
fun RecipeApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showBottomBar = currentRoute in listOf(
        ExploreDestination.route,
        SavedDestination.route
    ) || currentRoute?.startsWith(SearchDestination.route) == true

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
                    navController.navigate(SearchDestination.withSearchBar()) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                onFilterClick = { dietaryAttribute ->
                    navController.navigate(SearchDestination.withFilter(dietaryAttribute)) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable(SearchDestination.route) { backStackEntry ->
            val openWith = backStackEntry.arguments?.getString(SearchDestination.PARAM_OPEN_WITH)

            SearchScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(RecipeDetailsDestination.createRoute(recipeId))
                },
                params = getSearchScreenParam(openWith)
            )
        }

        composable(SavedDestination.route) {
            SavedScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(RecipeDetailsDestination.createRoute(recipeId))
                }
            )
        }

        composable(RecipeDetailsDestination.route) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString(RecipeDetailsDestination.PARAM_RECIPE_ID)
                ?: return@composable
            RecipeDetailsScreen(
                onBackClick = { navController.popBackStack() },
                recipeId = recipeId
            )
        }
    }
}