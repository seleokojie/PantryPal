package edu.towson.cosc435.meegan.semesterprojectpantrypal

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import edu.towson.cosc435.meegan.semesterprojectpantrypal.ui.theme.lightGreen
import edu.towson.cosc435.meegan.semesterprojectpantrypal.ui.theme.mediumGreen

// BottomNavigationBar Composable function that defines the navigation items and behavior
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Grocery,
        NavigationItem.Add,
        NavigationItem.Settings
    )
    //We want to use a Surface to apply a background color to the BottomNavigation and apply a darker color to the elevation shadow
    Surface(elevation = 12.dp, color = Color(0xFF000000)) {

        BottomNavigation(
            backgroundColor = Color.White,
            contentColor = Color.White
        ) {
            // Iterate through the navigation items and create BottomNavigationItem for each
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { item ->
                BottomNavigationItem(
                    icon = { item.icon?.let { Icon(it, contentDescription = item.title) } },
                    label = { Text(text = item.title) },
                    selectedContentColor = mediumGreen,
                    unselectedContentColor = lightGreen,
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route,
                    onClick = {
                        // Set up navigation behavior for each item
                        navController.navigate(item.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
