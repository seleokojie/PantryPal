package edu.towson.cosc435.meegan.semesterprojectpantrypal

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

//Description:
// Defines a sealed class called NavigationItem with three properties: route, icon, and title. Four
// instances of NavigationItem are created as objects, each representing a unique navigation item
// with a corresponding route, icon, and title.

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Home : NavigationItem("home", Icons.Filled.Home, "Home")
    object Grocery : NavigationItem("grocery", Icons.Filled.ShoppingCart, "Grocery")
    object Add : NavigationItem("add", Icons.Filled.Add, "Add")
    object Settings : NavigationItem("settings", Icons.Filled.Settings, "Settings")
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