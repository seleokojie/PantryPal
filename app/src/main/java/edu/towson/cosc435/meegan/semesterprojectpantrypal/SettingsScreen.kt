
package edu.towson.cosc435.meegan.semesterprojectpantrypal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun SettingsScreen() {
    val logoutButtonPressed = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3D3D3))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Preferences",
            color = Color.Blue,
            fontSize = 20.sp, // Make the text bigger
            modifier = Modifier
                .align(Alignment.Start)
        )

        SettingItem(icon = Icons.Default.Person, text = "Account")
        Divider() // Add a line separating settings
        SettingItem(icon = Icons.Default.Notifications, text = "Notifications")
        Divider() // Add a line separating settings
        SettingItem(icon = Icons.Default.Email, text = "Help")
        Divider() // Add a line separating settings
        Text(
            text = "Information",
            color = Color.Blue,
            fontSize = 20.sp, // Make the text bigger
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp)
        )

        SettingItem(icon = Icons.Default.Info, text = "About Us")
        Divider() // Add a line separating settings

        Button(
            onClick = { /* sign out action */ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = "Sign out")
            Text("Sign out of Pantry Pal", color = Color.White)
        }
    }
}

@Composable
fun SettingItem(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 18.sp) // Make the text bigger
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}
