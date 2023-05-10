package edu.towson.cosc435.meegan.semesterprojectpantrypal

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@Composable
fun ApplicationSwitcher() {
    val vm = UserState.current
    val context = LocalContext.current

    if (vm.isLoggedIn) {
        MainScreen()
    } else {
        LoginScreenWrapper()
    }
}