package edu.towson.cosc435.meegan.semesterprojectpantrypal

// used to create bottomNavigation bar https://johncodeos.com/how-to-create-bottom-navigation-bar-with-jetpack-compose/
// Command + Shift + '-' to minimize parts of code
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

/**
 * This file defines the main structure and navigation of the application. It includes the
 * MainActivity class, which sets the main content view, and Composable functions for the
 * MainScreen, TopBar, BottomNavigationBar, and Navigation. The navigation system is built
 * using Jetpack Compose's NavController, and each screen can be accessed via bottom navigation items.
 */


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

// Extension function to convert a HEX color string to a Jetpack Compose Color
fun String.toColor() = Color(android.graphics.Color.parseColor(this))

// Preview function for MainScreen
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}


