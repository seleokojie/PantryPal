package edu.towson.cosc435.meegan.semesterprojectpantrypal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun AddScreen() {


    // Initialize a mutable list of items from the App State
    val items = remember { AppState.items.toMutableList() }

    // Initialize a list of fields that users can fill in to add a new item
    val fields = listOf(
        "Item name" to remember { mutableStateOf(TextFieldValue("")) },
        "Category" to remember { mutableStateOf(TextFieldValue("")) },
        "Quantity" to remember { mutableStateOf(TextFieldValue("")) },
        "Expiration Date (YYYY-MM-DD)" to remember { mutableStateOf(TextFieldValue("")) }
    )

    // Initialize mutable state variables for the confirmation message and whether to show it
    val confirmationMessage = remember { mutableStateOf("") }
    val showMessage = remember { mutableStateOf(false) }

    // Define a composable function for the input fields
    @Composable
    fun InputField(
        label: String,
        state: MutableState<TextFieldValue>,
        modifier: Modifier = Modifier,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default
    ) {
        TextField(
            value = state.value,
            onValueChange = { state.value = it },
            label = { Text(label) },
            modifier = modifier,
            keyboardOptions = keyboardOptions
        )

        Spacer(modifier = Modifier.height(8.dp))
    }

    // Define the layout for the add screen
    Column(

        modifier = Modifier
            .fillMaxSize()
            .background("#e3ffde".toColor())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Handle barcode scanner functionality here
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.80f),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
        ) {
            Text("Barcode Scanner", color = Color.Black, fontSize = 19.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Display the input fields
        fields.forEach { (label, state) ->
            val keyboardType =
                if (label == "Quantity" || label == "Expiration Date (MM-DD-YYYY)") KeyboardType.Number else KeyboardType.Text
            InputField(label, state, keyboardOptions = KeyboardOptions(keyboardType = keyboardType))
        }

        // Display a button to confirm the item and add it to the list
        Button(onClick = {
            // Create a new item from the input fields and add it to the list of items
            val newItem = Item(
                fields[0].second.value.text,
                fields[1].second.value.text,
                fields[2].second.value.text,
                fields[3].second.value.text
            )
            items.add(newItem)

            // Update the App State with the new list of items
            AppState.items = items.toList()

            // Reset the input fields to blank
            fields.forEach { (_, state) -> state.value = TextFieldValue("") }

            // Update the confirmation message and show it
            confirmationMessage.value = "Item added to inventory"
            showMessage.value = true
        }) {
            Text("Confirm")
        }

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            if (items.isNotEmpty()) {
                Text(
                    text = "Current Inventory",
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }


        // Display the list of items in a LazyColumn
        Spacer(modifier = Modifier.height(4.dp))
        LazyColumn {
            items.forEach { item ->
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                            .background(Color.White)
                            .padding(8.dp)
                    ) {
                        Text(
                            "Name: ${item.name}\nCategory: ${item.category}\nQuantity: ${item.quantity}\nEXP: ${item.expirationDate}",
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }


        // Display a confirmation message if an item was successfully added
        Spacer(modifier = Modifier.height(16.dp))
        ConfirmationMessage(
            message = confirmationMessage.value,
            showMessage = showMessage.value,
            onMessageShown = { showMessage.value = false }
        )
    }
}


data class Item(
    val name: String,
    val category: String,
    val quantity: String,
    val expirationDate: String
)



@Composable
fun ConfirmationMessage(
    message: String,
    showMessage: Boolean,
    onMessageShown: () -> Unit
) {
    if (showMessage) {
        LaunchedEffect(Unit) {
            delay(3000) // Adjust the duration to your preference
            onMessageShown()
        }
        Text(message, Modifier.padding(top = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    AddScreenPreview()
}