package edu.towson.cosc435.meegan.semesterprojectpantrypal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

//Description:
// This code snippet defines a ContentScreens class and four Composable functions: HomeScreen,
// GroceryScreen, AddScreen, and SettingsScreen. Each function represents a unique content screen
// within the app, displaying a centered text with the screen's name. The background color of these
// screens is set to a custom green color, and the text is bold and sized at 25.sp. Additionally,
// @Preview annotations are used to provide previews of the screens within the Android Studio design
// surface.

//class ContentScreens {
//}
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background("#e3ffde".toColor())
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Home View",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

class GroceryViewModel : ViewModel() {
    val groceryItems = mutableStateListOf<String>()
    val newItemText = mutableStateOf("")
    val checkedStates = mutableStateMapOf<String, Boolean>()
}
@Composable
fun GroceryScreen() {
    val viewModel: GroceryViewModel = viewModel()
    val groceryItems = viewModel.groceryItems
    val newItemText = viewModel.newItemText
    val checkedStates = viewModel.checkedStates

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background("#e3ffde".toColor())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            TextField(
                value = newItemText.value,
                onValueChange = { newItemText.value = it },
                label = { Text("Add item to Grocery list") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    if (newItemText.value.isNotBlank()) {
                        groceryItems.add(newItemText.value)
                        newItemText.value = ""
                    }
                }),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(groceryItems) { item ->
                    checkedStates.putIfAbsent(item, false)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .border(1.dp, Color.LightGray, RoundedCornerShape(7.dp))
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Checkbox(
                            checked = checkedStates[item] == true,
                            onCheckedChange = { checked ->
                                checkedStates[item] = checked
                            },
                            modifier = Modifier.padding(end = 8.dp),
                            colors = CheckboxDefaults.colors(
                                checkmarkColor = Color.White,
                                checkedColor = Color.Black,
                                uncheckedColor = Color.Black
                            )
                        )

                        Text(
                            text = item,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center),
                            textDecoration = if (checkedStates[item] == true) TextDecoration.LineThrough else TextDecoration.None
                        )
                        IconButton(onClick = {
                            checkedStates.remove(item)
                            groceryItems.remove(item)
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete item")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroceryScreenPreview() {
    GroceryScreen()
}


data class Item(
    val name: String,
    val category: String,
    val quantity: String,
    val expirationDate: String
)
//NOTE: go back and add if else clauses for blank inputs
@Composable
fun AddScreen(onItemAdded: (String, String, String, String) -> Unit) {
    val fields = listOf(
        "Item name" to remember { mutableStateOf(TextFieldValue("")) },
        "Category" to remember { mutableStateOf(TextFieldValue("")) },
        "Quantity" to remember { mutableStateOf(TextFieldValue("")) },
        "Expiration Date (YYYY-MM-DD)" to remember { mutableStateOf(TextFieldValue("")) }
    )
    val confirmationMessage = remember { mutableStateOf("") }
    val showMessage = remember { mutableStateOf(false) }

    @Composable
    fun InputField(
        label: String,
        state: MutableState<TextFieldValue>,
        modifier: Modifier = Modifier,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default)
    {
        TextField(
            value = state.value,
            onValueChange = { state.value = it },
            label = { Text(label) },
            modifier = modifier,
            keyboardOptions = keyboardOptions)

        Spacer(modifier = Modifier.height(8.dp))
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
            val keyboardType = if (label == "Quantity" || label == "Expiration Date (YYYY-MM-DD)") KeyboardType.Number else KeyboardType.Text
            InputField(label, state, keyboardOptions = KeyboardOptions(keyboardType = keyboardType))
        }

        Button(onClick = {
            onItemAdded(
                fields[0].second.value.text,
                fields[1].second.value.text,
                fields[2].second.value.text,
                fields[3].second.value.text
            )


            // Reset the input fields to blank
            fields.forEach { (_, state) -> state.value = TextFieldValue("") }

            // Update the confirmation message and show it
            confirmationMessage.value = "Item added to inventory"
            showMessage.value = true
        }) {
            Text("Confirm")
        }
        ConfirmationMessage(
            message = confirmationMessage.value,
            showMessage = showMessage.value,
            onMessageShown = { showMessage.value = false }
        )
    }
}


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
    AddScreen(onItemAdded = { itemName, category, quantity, expirationDate ->
    })
}


@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background("#e3ffde".toColor())
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Settings View",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}

