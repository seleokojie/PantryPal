package edu.towson.cosc435.meegan.semesterprojectpantrypal

import androidx.compose.runtime.Composable


@Composable
fun ApplicationSwitcher() {
    val vm = UserState.current
    if (vm.isLoggedIn) {
        MainScreen()
    } else {
        LoginScreen()
    }
}