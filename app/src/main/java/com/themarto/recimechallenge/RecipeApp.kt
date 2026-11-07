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
import com.themarto.recimechallenge.ui.features.ExploreScreen
import com.themarto.recimechallenge.ui.features.RecipeDetailsScreen
import com.themarto.recimechallenge.ui.features.SavedScreen
import com.themarto.recimechallenge.ui.features.SearchScreen

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
            ExploreScreen()
        }
        composable(Destinations.Search.route) {
            SearchScreen()
        }
        composable(Destinations.Saved.route) {
            SavedScreen()
        }
        composable(Destinations.RecipeDetails.route) {
            RecipeDetailsScreen()
        }
    }
}