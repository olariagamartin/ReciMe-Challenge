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
import com.themarto.recimechallenge.navigation.Destinations
import com.themarto.recimechallenge.features.explore.presentation.ExploreScreen
import com.themarto.recimechallenge.features.recipe_details.presentation.RecipeDetailsScreen
import com.themarto.recimechallenge.features.SavedScreen
import com.themarto.recimechallenge.features.search.presentation.SearchScreen

@Composable
fun RecipeApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showBottomBar = currentRoute in listOf(
        Destinations.Explore.route,
        Destinations.Search.route,
        Destinations.Saved.route
    )
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
        startDestination = Destinations.Explore.route,
        modifier = modifier
    ) {
        composable(Destinations.Explore.route) {
            ExploreScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(Destinations.RecipeDetails.createRoute(recipeId))
                },
                onSearchClick = {
                    navController.navigate(Destinations.Search.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                }
            )
        }
        composable(Destinations.Search.route) {
            SearchScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(Destinations.RecipeDetails.createRoute(recipeId))
                }
            )
        }
        composable(Destinations.Saved.route) {
            SavedScreen()
        }
        composable(Destinations.RecipeDetails.route) {
            RecipeDetailsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}