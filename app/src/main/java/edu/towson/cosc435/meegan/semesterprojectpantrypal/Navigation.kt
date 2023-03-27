package edu.towson.cosc435.meegan.semesterprojectpantrypal

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class Navigation {
}
// Navigation Composable function that sets up the NavHost and routes for each screen
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
        composable(NavigationItem.Grocery.route) {
            GroceryScreen()
        }
        composable(NavigationItem.Add.route) {
            AddScreen(
            )
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen()
        }
    }
}
