
package edu.towson.cosc435.meegan.semesterprojectpantrypal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.towson.cosc435.meegan.semesterprojectpantrypal.ui.theme.defaultBackgroundColor

@Composable
fun MainScreen() {


    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            //topBar will display only for Add screen
            if (currentRoute == NavigationItem.Add.route) {
                TopBar()
            }
            else if (currentRoute == NavigationItem.Home.route){
                SearchBar(label = "Search", hintText = "Type to search...")
            }
        },
        bottomBar = { BottomNavigationBar(navController) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController = navController)
            }
        },
        backgroundColor = defaultBackgroundColor
    )
}

// Preview function for MainScreen
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}