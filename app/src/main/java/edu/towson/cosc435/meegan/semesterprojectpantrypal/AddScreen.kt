package edu.towson.cosc435.meegan.semesterprojectpantrypal

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.*
@Composable
fun AddScreen() {

    //For Date
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var selectedDateText by remember { mutableStateOf("") }
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    val databaseHelper = MyDatabaseHelper(context)

    //For InputFields
    val items = remember { AppState.items.toMutableList() }
    val itemNameState = remember { mutableStateOf(TextFieldValue("")) }
    val categoryState = remember { mutableStateOf(TextFieldValue("")) }
    val quantityState = remember { mutableStateOf(TextFieldValue("")) }
    val expirationDateState = remember { mutableStateOf(TextFieldValue("")) }
    val errorState = remember { mutableStateOf(false) }
    val categories = listOf("Fruit", "Vegetable", "Meat", "Dairy", "Bakery")
    //Choosing of Date
    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "${selectedMonth + 1}-$selectedDayOfMonth-$selectedYear"
            expirationDateState.value = TextFieldValue(selectedDateText)
        }, year, month, dayOfMonth
    )

    //Can't choose past dates
    datePicker.datePicker.minDate = calendar.timeInMillis

    //Item added confirmation
    val confirmationMessage = remember { mutableStateOf("") }
    val showMessage = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background("#e3ffde".toColor())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { /* Handle barcode scanner functionality here */ },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.80f),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
        ) {
            Text("Barcode Scanner", color = Color.Black, fontSize = 19.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        InputField("Item name", itemNameState, onlyLet = true)
        InputField("Category", categoryState, categories = categories, onlyLet = true)
        InputField("Quantity", quantityState, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), onlyNum = true)
        InputField(
            "Expiration Date (MM-DD-YYYY)",
            expirationDateState,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onClick = {
                datePicker.show()
            }
        )
        Button(onClick = {
            if (itemNameState.value.text.isBlank() || categoryState.value.text.isBlank() || quantityState.value.text.isBlank() || expirationDateState.value.text.isBlank()) {
                errorState.value = true
                return@Button
            } else {
                errorState.value = false
            }

            val newItem = Item(
                AppState.loggedInUserId,
                itemNameState.value.text,
                categoryState.value.text,
                quantityState.value.text,
                expirationDateState.value.text
            )
            databaseHelper.addItem(newItem)
            AppState.items = items.toList()
            confirmationMessage.value = "Item added to inventory"
            showMessage.value = true

            // Clear the input fields
            itemNameState.value = TextFieldValue("")
            categoryState.value = TextFieldValue("")
            quantityState.value = TextFieldValue("")
            expirationDateState.value = TextFieldValue("")
        }){
            Text("Confirm")
        }

        if (errorState.value) {
            Text("Please fill out all fields",
                color = Color.Red,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,

            )
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

@Composable
fun InputField(
    label: String,
    state: MutableState<TextFieldValue>,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onlyNum: Boolean = false,
    onlyLet: Boolean = false,
    categories: List<String> = emptyList(),
    onClick: (() -> Unit)? = null
)
{
    var selectedIndex by remember { mutableStateOf(-1) }

    Column(modifier) {
        if (categories.isNotEmpty()) {
            var expanded by remember { mutableStateOf(false) }

            Box(Modifier.clickable(onClick = { expanded = true })) {
                TextField(
                    value = if (selectedIndex >= 0) state.value else TextFieldValue(""),
                    onValueChange = { newValue ->
                        if (onlyNum && newValue.text.any { !it.isDigit() }) return@TextField
                        if (onlyLet && newValue.text.any { !it.isLetter() && it != ' ' }) return@TextField
                        state.value = newValue
                    },
                    label = { Text(label) },
                    modifier = Modifier.fillMaxWidth(0.7f),
                    keyboardOptions = keyboardOptions,
                    enabled = false
                )
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                categories.forEachIndexed { index, category ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        state.value = TextFieldValue(category)
                        expanded = false
                    }) {
                        Text(text = category)
                    }
                }
            }
        } else {
            if (onClick != null) {
                Box(Modifier.clickable(onClick = onClick).fillMaxWidth(0.7f)) {
                    TextField(
                        value = state.value,
                        onValueChange = { newValue ->
                            if (onlyNum && newValue.text.any { !it.isDigit() }) return@TextField
                            if (onlyLet && newValue.text.any { !it.isLetter() && it != ' ' }) return@TextField
                            state.value = newValue
                        },
                        label = { Text(label) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = keyboardOptions,
                        enabled = false
                    )
                }
            } else {
                TextField(
                    value = state.value,
                    onValueChange = { newValue ->
                        if (onlyNum && newValue.text.any { !it.isDigit() }) return@TextField
                        if (onlyLet && newValue.text.any { !it.isLetter() && it != ' ' }) return@TextField
                        state.value = newValue
                    },
                    label = { Text(label) },
                    modifier = Modifier.fillMaxWidth(0.7f),
                    keyboardOptions = keyboardOptions
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

data class Item(
    val userId: Int,
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