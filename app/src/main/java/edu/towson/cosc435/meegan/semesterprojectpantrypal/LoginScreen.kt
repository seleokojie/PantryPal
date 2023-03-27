package edu.towson.cosc435.meegan.semesterprojectpantrypal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//@Composable
//fun LoginScreen(navController: NavHostController) {
//    // State variables for email and password input
//    val emailState = remember { mutableStateOf("") }
//    val passwordState = remember { mutableStateOf("") }
//
//    // UI
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "Login",
//            style = MaterialTheme.typography.h4,
//            modifier = Modifier.padding(bottom = 24.dp)
//        )
//        TextField(
//            value = emailState.value,
//            onValueChange = { emailState.value = it },
//            label = { Text("Email") },
//            modifier = Modifier.fillMaxWidth(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        TextField(
//            value = passwordState.value,
//            onValueChange = { passwordState.value = it },
//            label = { Text("Password") },
//            modifier = Modifier.fillMaxWidth(),
//            visualTransformation = PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            onClick = {
//                navController.navigate(NavigationItem.Main.route) {
//                    popUpTo(NavigationItem.Login.route) {
//                        inclusive = false
//                    }
//                }
//            }
//        ) {
//            Text("Login")
//        }
//}}

@Composable
fun LoginScreen(
    username: String,
    password: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginButtonClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .widthIn(min = 300.dp)
                .heightIn(min = 400.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Log in",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(16.dp)
                )

                // Create text fields for username and password
                OutlinedTextField(
                    value = username,
                    onValueChange = onUsernameChange,
                    label = { Text("Username") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text("Password") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )

                // Create button to log in
                Button(
                    onClick = onLoginButtonClick,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Log in")
                }

                //Doesnt do anything atm
                // Create clickable "Sign up" text
                Text(
                    text = "Don't have an account? Sign up",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable(onClick = onSignUpClick)
                )
            }
        }
    }
}

