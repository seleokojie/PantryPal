package edu.towson.cosc435.meegan.semesterprojectpantrypal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun AddScreen() {
    val items = remember { AppState.items.toMutableList() }

    val fields = listOf(
        "Item name" to remember { mutableStateOf(TextFieldValue("")) },
        "Category" to remember { mutableStateOf(TextFieldValue("")) },
        "Quantity" to remember { mutableStateOf(TextFieldValue("")) },
        "Expiration Date (YYYY-MM-DD)" to remember { mutableStateOf(TextFieldValue("")) }
    )

    val confirmationMessage = remember { mutableStateOf("") }
    val showMessage = remember { mutableStateOf(false) }

    @Composable
    fun InputField(label: String, state: MutableState<TextFieldValue>, keyboardType: KeyboardType) {
        TextField(
            value = state.value,
            onValueChange = { state.value = it },
            label = { Text(label) },
            modifier = Modifier.padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background("#e3ffde".toColor())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        fields.forEach { (label, state) ->
            val keyboardType =
                if (label == "Quantity" || label == "Expiration Date (MM-DD-YYYY)") KeyboardType.Number else KeyboardType.Text
            InputField(label, state, keyboardType)
        }

        Button(onClick = {
            val newItem = Item(
                fields[0].second.value.text,
                fields[1].second.value.text,
                fields[2].second.value.text,
                fields[3].second.value.text
            )
            items.add(newItem)
            AppState.items = items.toList()
            fields.forEach { (_, state) -> state.value = TextFieldValue("") }
            confirmationMessage.value = "Item added to inventory"
            showMessage.value = true
        }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Confirm")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Current Inventory",
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        LazyColumn {
            items.forEach { item ->
                item {
                    Text(
                        "Name: ${item.name}\nCategory: ${item.category}\nQuantity: ${item.quantity}\nEXP: ${item.expirationDate}",
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

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