
package edu.towson.cosc435.meegan.semesterprojectpantrypal

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@Composable
fun SettingsScreen() {
    val showHelpDialog = remember { mutableStateOf(false) }
    val showAccountDropdown = remember { mutableStateOf(false) }
    val showNotificationDropDown = remember { mutableStateOf(false) }
    val showAboutUsDropdown = remember { mutableStateOf(false)}
    val logoutButtonPressed = remember { mutableStateOf(false) }
    val vm = UserState.current
    val coroutineScope = rememberCoroutineScope()
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

        SettingItem(
            icon = Icons.Default.Person,
            text = "Account",
            onExpandedChange = { showAccountDropdown.value = !showAccountDropdown.value }
        )
        if (showAccountDropdown.value) {
            ExpandableSettingList()
        }
        Divider() // Add a line separating settings
        SettingItem(
            icon = Icons.Default.Notifications,
            text = "Notifications",
            onExpandedChange = {showNotificationDropDown.value = !showNotificationDropDown.value}
        )
        if (showNotificationDropDown.value) {
            ExpandableNotificationsList()
        }
        Divider() // Add a line separating settings
        SettingItem(
            icon = Icons.Default.Email,
            text = "Help",
            onClick = {
                Log.d("help icon", "clicked")
                showHelpDialog.value = true }
        )
        if (showHelpDialog.value) {
            AlertDialog(
                onDismissRequest = { showHelpDialog.value = false },
                title = { Text("Help") },
                text = { Text("Contact support at supportpantrypal.com") },
                confirmButton = {
                    Button(onClick = { showHelpDialog.value = false } )
                    {
                        Text("Ok")
                    }
                }
            )
        }
        Divider() // Add a line separating settings
        Text(
            text = "Information",
            color = Color.Blue,
            fontSize = 20.sp, // Make the text bigger
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp)
        )

        SettingItem(
            icon = Icons.Default.Info,
            text = "About Us",
            onExpandedChange = {showAboutUsDropdown.value = !showAboutUsDropdown.value}
        )
        if (showAboutUsDropdown.value) {
            ExpandableAboutUs()
        }
        Divider() // Add a line separating settings

        Button(
            onClick = {
                coroutineScope.launch {
                    vm.signOut()
                }},
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = "Sign out")
            Text("Sign out of Pantry Pal", color = Color.White)
        }
    }
}

@Composable
fun SettingItem(icon: ImageVector, text: String, onClick: () -> Unit = {}, onExpandedChange: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable { onClick(); onExpandedChange() },
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

@Composable
fun ExpandableSettingList() {
    Column (modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)){
        SettingItem(icon = Icons.Default.AccountBox, text = "Profile")
        SettingItem(icon = Icons.Default.Lock, text = "Security")
    }
}

@Composable
fun ExpandableNotificationsList() {
    Column (modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)){
        SettingItem(icon = Icons.Default.AccountBox, text = "Show Alerts")
    }
}

@Composable
fun ExpandableAboutUs() {
    Column(
        modifier =
        Modifier
            .background(Color.White)
            .padding(16.dp)
    )  {
        Text(
            text = "Pantry Pal helps users manage their food inventory and reduce food waste by notifying them when items are close to expiration. The app allows users to easily add, update, and delete items, filter their inventory by perishable or non-perishable items, and search for specific items by name. Users can also use the app as a grocery list and note-taker, and scan barcodes to quickly fill in item information.",
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}
