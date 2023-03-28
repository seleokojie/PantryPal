package edu.towson.cosc435.meegan.semesterprojectpantrypal

// used to create bottomNavigation bar https://johncodeos.com/how-to-create-bottom-navigation-bar-with-jetpack-compose/
// Command + Shift + '-' to minimize parts of code
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val username = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }
            val loginButtonPressed = remember { mutableStateOf(false) }

            // Create a boolean variable to indicate whether the MainScreen should be displayed
            val showMainScreen =
                username.value.isNotEmpty() && password.value.isNotEmpty() && loginButtonPressed.value

            if (showMainScreen) {
                MainScreen()
            } else {
                LoginScreen(
                    username = username.value,
                    password = password.value,
                    onUsernameChange = { username.value = it },
                    onPasswordChange = { password.value = it },
                    onLoginButtonClick = { loginButtonPressed.value = true },
                    onSignUpClick = { /* handle sign up f low */ }
                )
            }
        }
    }
}

// Extension function to convert a HEX color string to a Jetpack Compose Color
fun String.toColor() = Color(android.graphics.Color.parseColor(this))





