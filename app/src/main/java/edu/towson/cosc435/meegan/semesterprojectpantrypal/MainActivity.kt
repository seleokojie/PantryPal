package edu.towson.cosc435.meegan.semesterprojectpantrypal

// used to create bottomNavigation bar https://johncodeos.com/how-to-create-bottom-navigation-bar-with-jetpack-compose/
// Command + Shift + '-' to minimize parts of code

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import edu.towson.cosc435.meegan.semesterprojectpantrypal.ui.theme.SemesterProjectPantryPalTheme


class MainActivity : ComponentActivity() {
    private val userState by viewModels<UserStateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SemesterProjectPantryPalTheme {
                 CompositionLocalProvider(UserState provides userState ) {
                     ApplicationSwitcher()
                 }
            }
        }
    }
}





