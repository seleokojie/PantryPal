package edu.towson.cosc435.meegan.semesterprojectpantrypal

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Home : NavigationItem("home", Icons.Filled.Home, "Home")
    object Grocery : NavigationItem("grocery", Icons.Filled.ShoppingCart, "Grocery")
    object Add : NavigationItem("add", Icons.Filled.Add, "Add")
    object Settings : NavigationItem("settings", Icons.Filled.Settings, "Settings")
}
