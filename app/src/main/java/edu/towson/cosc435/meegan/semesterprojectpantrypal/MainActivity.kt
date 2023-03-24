package edu.towson.cosc435.meegan.semesterprojectpantrypal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


// used to create bottomNavigation bar https://johncodeos.com/how-to-create-bottom-navigation-bar-with-jetpack-compose/
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

//I like using RGB and HEX values for color

fun String.toColor() = Color(android.graphics.Color.parseColor(this))
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute == NavigationItem.Add.route) {
                TopBar()
            }
        },
        bottomBar = { BottomNavigationBar(navController) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController = navController)
            }
        },
        backgroundColor = "#e3ffde".toColor()
    )
}

//backgroundColor = "#e3ffde".toColor()
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

//will only show when add is the currentRoute
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Add New Item", fontSize = 18.sp)
            }
        },
        backgroundColor = "#75b37c".toColor(),
        contentColor = Color.Black,
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}


//@Composable
//fun BottomNavigationBar(navController: NavHostController) {
//    val items = listOf(
//        NavigationItem.Home,
//        NavigationItem.Grocery,
//        NavigationItem.Add,
//        NavigationItem.Settings
//    )
//    BottomNavigation(
//        backgroundColor = "#75b37c".toColor(),
//        contentColor = Color.White
//    ) {
//        items.forEach { item ->
//            BottomNavigationItem(
//                icon = { Icon(item.icon, contentDescription = item.title) },
//                label = { Text(text = item.title) },
//                selectedContentColor = Color.White,
//                unselectedContentColor = Color.White.copy(0.4f),
//                alwaysShowLabel = true,
//                selected = false,
//                onClick = {
//                    /* Add code later */
//                }
//            )
//        }
//    }
//}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Grocery,
        NavigationItem.Add,
        NavigationItem.Settings
    )
    BottomNavigation(
        backgroundColor = "#75b37c".toColor(),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
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
            AddScreen()
        }
        composable(NavigationItem.Settings.route) {
           SettingsScreen()
        }
    }
}
@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
   // BottomNavigationBar(navController)
}
