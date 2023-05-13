package edu.towson.cosc435.meegan.semesterprojectpantrypal

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

// no need to make async at moment but may in the future when interacting with database

@Composable
fun LoginScreen(context: Context) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val vm = UserState.current
    val dbHelper = MyDatabaseHelper(context)
    val appIcon = painterResource(id = R.drawable.logo_fridge)

    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (vm.isBusy) {
            Image(
                painter = appIcon,
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(128.dp)
                    .padding(16.dp)
            )
            CircularProgressIndicator()
        } else {
            Text(
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                text = "Login",
            )
            TextField(
                value = email,
                onValueChange = { newEmail ->
                    email = newEmail
                },
                label = { Text("Email") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { newPassword ->
                    password = newPassword
                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(

                onClick = {
                    println("\n\n\n$email")
                    println("$password\n\n\n")
                    val authenticated = dbHelper.authenticateUser(email, password)
                    if (authenticated) {
                        coroutineScope.launch {
                            vm.signIn(email, password)
                        }
                    } else {
                        Toast.makeText(context, "Incorrect login details", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Login")
            }
        }
    }
}

@Composable
fun LoginScreenWrapper() {
    val context = LocalContext.current
    LoginScreen(context)
}