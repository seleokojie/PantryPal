package edu.towson.cosc435.meegan.semesterprojectpantrypal

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.roundToInt

@SuppressLint("CoroutineCreationDuringComposition", "UnrememberedMutableState")
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
    val nutrientsState = remember { mutableStateOf<Nutrients?>(null) }
    val baseNutrients = remember { mutableStateOf(nutrientsState.value) }
    val caloriesState = remember { mutableStateOf(TextFieldValue("")) }
    val proteinState = remember { mutableStateOf(TextFieldValue("")) }
    val fatState = remember { mutableStateOf(TextFieldValue("")) }
    val carbsState = remember { mutableStateOf(TextFieldValue("")) }
    val fiberState = remember { mutableStateOf(TextFieldValue("")) }
    val errorState = remember { mutableStateOf(false) }

    val categories = listOf(
        "Baking Ingredients and Supplies",
        "Beans, Peas, and Lentils",
        "Beverages",
        "Breakfast Foods",
        "Canned Goods",
        "Cheese",
        "Condiments",
        "Dairy",
        "Dessert",
        "Eggs",
        "Frozen Foods",
        "Fruit",
        "Grains (Refined)",
        "Grains (Whole)",
        "Juice",
        "Legumes",
        "Meats (Processed)",
        "Meats (Red)",
        "Meats (White)",
        "Non-Dairy Calcium Alternatives",
        "Nuts and Seeds",
        "Oils",
        "Other",
        "Pasta",
        "Poultry",
        "Sauce",
        "Seafood",
        "Snacks and Treats",
        "Soda",
        "Soy Products",
        "Spices and Herbs",
        "Vegetables (Dark-Green)",
        "Vegetables (Red and Orange)",
        "Vegetables (Starchy)",
        "Vegetables (Other)",
        "Yogurt",
    )

    //Choosing of Date
    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedYear-${if (selectedMonth + 1 < 10) ("0" + (selectedMonth + 1)) else (selectedMonth + 1)}-$selectedDayOfMonth"

            expirationDateState.value = TextFieldValue(selectedDateText)
        }, dayOfMonth, month, year
    )
    //Can't choose past dates
    datePicker.datePicker.minDate = calendar.timeInMillis

    //Item added confirmation
    val confirmationMessage = remember { mutableStateOf("") }
    val showMessage = remember { mutableStateOf(false) }

    //For AutoCompleteTextView
    val autoCompleteItems = remember { mutableStateListOf<String>() }
    val selectedItemIndex = remember { mutableStateOf(-1) }
    val isItemNameDropdownVisible = remember { mutableStateOf(false) }
    val isCategoryDropdownVisible = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Item",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(16.dp))

        //Use the AutoCompleteTextView composable to create an autocomplete text field
        AutoCompleteTextView(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            query = itemNameState.value.text,
            queryLabel = "Look Up Food Item",
            onQueryChanged = { it ->
                itemNameState.value = TextFieldValue(it)

                // Call the getFoodList function using a coroutine scope
                coroutineScope.launch {
                    val foodList = withContext(Dispatchers.IO) {
                        getFoodList(it)?.hints?.map { it.food.label }?.distinct()
                            ?.sortedBy { it.length } ?: listOf()
                    }
                    autoCompleteItems.clear()
                    autoCompleteItems.addAll(foodList)
                }
                isItemNameDropdownVisible.value = true
                isCategoryDropdownVisible.value = false
            },
            predictions = autoCompleteItems,
            onItemClick = { selectedItem ->
                itemNameState.value = TextFieldValue(selectedItem)
                isItemNameDropdownVisible.value = false
                autoCompleteItems.clear()
                selectedItemIndex.value = 0

                coroutineScope.launch {
                    val foodList = withContext(Dispatchers.IO) {
                        getFoodList(selectedItem)
                    }
                    val selectedFoodHint = foodList?.hints?.find { it.food.label == selectedItem }

                    // Autofill the nutrient fields based on the selected item
                    val selectedNutrients = selectedFoodHint?.food?.nutrients
                    nutrientsState.value = selectedNutrients
                    caloriesState.value = TextFieldValue(selectedNutrients?.ENERC_KCAL?.toInt().toString())
                    proteinState.value = TextFieldValue(selectedNutrients?.PROCNT?.roundToNearestTens().toString())
                    fatState.value = TextFieldValue(selectedNutrients?.FAT?.roundToNearestTens().toString())
                    carbsState.value = TextFieldValue(selectedNutrients?.CHOCDF?.roundToNearestTens().toString())
                    fiberState.value = TextFieldValue(selectedNutrients?.FIBTG?.roundToNearestTens().toString())

                    baseNutrients.value = selectedNutrients
                }
            },
            itemContent = { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            },
            onClearClick = {
                itemNameState.value = TextFieldValue("")
                isItemNameDropdownVisible.value = false
                autoCompleteItems.clear()
            },
            isDropdownVisible = isItemNameDropdownVisible.value
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Show the remaining fields only if an item is selected
        if (selectedItemIndex.value != -1) {
            //Add an autocomplete text field for the category. Use the categories list as the predictions
            AutoCompleteTextView(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                query = categoryState.value.text,
                queryLabel = "Category",
                onQueryChanged = { it ->
                    categoryState.value = TextFieldValue(it)
                    autoCompleteItems.clear()
                    autoCompleteItems.addAll(categories.filter { category ->
                        category.contains(it, ignoreCase = true)
                    }.sortedBy { it.length }.take(3)) // Filter by shortest name and take 3
                    isCategoryDropdownVisible.value = true
                    isItemNameDropdownVisible.value = false
                },
                predictions = autoCompleteItems,
                onItemClick = { selectedItem ->
                    categoryState.value = TextFieldValue(selectedItem)
                    isCategoryDropdownVisible.value = false
                    autoCompleteItems.clear()
                    selectedItemIndex.value = 0
                },
                itemContent = { item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                },
                onClearClick = {
                    categoryState.value = TextFieldValue("")
                    isCategoryDropdownVisible.value = false
                    autoCompleteItems.clear()
                },
                isDropdownVisible = isCategoryDropdownVisible.value
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nutrients fields
            Row(Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    //Make quantity field but have it so that when a user types in a value to quantity, it multiplies the values in calories, protein, fat, carbs, and fiber by that amount
                    InputField(
                        "Quantity",
                        quantityState,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onlyNum = true,
                        onValueChanged = { newValue ->
                            val quantityText = newValue.text
                            val quantity = if (quantityText.isEmpty()) 0.0 else quantityText.toDoubleOrNull() ?: 0.0


                            if(quantity != 0.0) {
                                // Multiply the base values by the quantity
                                val multipliedNutrients = baseNutrients.value?.let { base ->
                                    base.copy(
                                        ENERC_KCAL = base.ENERC_KCAL * quantity,
                                        PROCNT = base.PROCNT * quantity,
                                        FAT = base.FAT * quantity,
                                        CHOCDF = base.CHOCDF * quantity,
                                        FIBTG = base.FIBTG * quantity
                                    )
                                }

                                // Update the nutrientsState with the multiplied values
                                nutrientsState.value = multipliedNutrients

                                // Update the respective text field values with the multiplied values
                                caloriesState.value = TextFieldValue(multipliedNutrients?.ENERC_KCAL?.toInt().toString())
                                proteinState.value = TextFieldValue(multipliedNutrients?.PROCNT?.roundToNearestTens().toString())
                                fatState.value = TextFieldValue(multipliedNutrients?.FAT?.roundToNearestTens().toString())
                                carbsState.value = TextFieldValue(multipliedNutrients?.CHOCDF?.roundToNearestTens().toString())
                                fiberState.value = TextFieldValue(multipliedNutrients?.FIBTG?.roundToNearestTens().toString())
                            } else {
                                // If the quantity is 0, reset the values to the base values
                                nutrientsState.value = baseNutrients.value
                                caloriesState.value = TextFieldValue(baseNutrients.value?.ENERC_KCAL?.toInt().toString())
                                proteinState.value = TextFieldValue(baseNutrients.value?.PROCNT?.roundToNearestTens().toString())
                                fatState.value = TextFieldValue(baseNutrients.value?.FAT?.roundToNearestTens().toString())
                                carbsState.value = TextFieldValue(baseNutrients.value?.CHOCDF?.roundToNearestTens().toString())
                                fiberState.value = TextFieldValue(baseNutrients.value?.FIBTG?.roundToNearestTens().toString())
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    InputField(
                        "Protein",
                        proteinState,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onlyNum = true
                    )

                }
                Column(modifier = Modifier.weight(1f)) {
                    InputField(
                        "Calories",
                        caloriesState,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onlyNum = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InputField(
                        "Fat",
                        fatState,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onlyNum = true
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    InputField(
                        "Carbs",
                        carbsState,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onlyNum = true
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    InputField(
                        "Fiber",
                        fiberState,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onlyNum = true
                    )
                }
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Row(Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        InputField(
                            "Expiration Date (MM-DD-YYYY)",
                            expirationDateState,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            onClick = { datePicker.show() }
                        )
                    }
                }
            }

            Button(onClick = {
                if (itemNameState.value.text.isBlank() || categoryState.value.text.isBlank() || quantityState.value.text.isBlank() || expirationDateState.value.text.isBlank() || caloriesState.value.text.isBlank() || proteinState.value.text.isBlank() || fatState.value.text.isBlank() || carbsState.value.text.isBlank() || fiberState.value.text.isBlank()) {
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
                    expirationDateState.value.text,
                    caloriesState.value.text.toDoubleOrNull(),
                    proteinState.value.text.toDoubleOrNull(),
                    fatState.value.text.toDoubleOrNull(),
                    carbsState.value.text.toDoubleOrNull(),
                    fiberState.value.text.toDoubleOrNull()
                )

                items.add(newItem)
                databaseHelper.addItem(newItem)
                AppState.items = items.toList()

                confirmationMessage.value = "Item added to inventory"
                showMessage.value = true

                // Clear the input fields
                itemNameState.value = TextFieldValue("")
                categoryState.value = TextFieldValue("")
                quantityState.value = TextFieldValue("")
                expirationDateState.value = TextFieldValue("")
                caloriesState.value = TextFieldValue("")
                proteinState.value = TextFieldValue("")
                fatState.value = TextFieldValue("")
                carbsState.value = TextFieldValue("")
                fiberState.value = TextFieldValue("")
                selectedItemIndex.value = -1
            }) {
                Text("Confirm")
            }

            if (errorState.value) {
                Text(
                    "Please fill out all fields",
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

        }

        // Display a confirmation message if an item was successfully added using a Dialog
        if (showMessage.value) {
            AlertDialog(
                onDismissRequest = { showMessage.value = false },
                title = { Text(text = confirmationMessage.value) },
                confirmButton = {
                    TextButton(onClick = { showMessage.value = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

//Uses the EdamamService to get a list of potential foods based on the user's input
suspend fun getFoodList(food: String): FoodDataResponse? {
    return withContext(Dispatchers.IO) {
        val edamamService = EdamamService("", "")
        edamamService.getFoodData(food)
    }
}

//Creates an autocomplete text field
@Composable
fun <T> AutoCompleteTextView(
    modifier: Modifier,
    query: String,
    queryLabel: String,
    onQueryChanged: (String) -> Unit = {},
    predictions: List<T>,
    onDoneActionClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onItemClick: (T) -> Unit = {},
    itemContent: @Composable (T) -> Unit = {},
    isDropdownVisible: Boolean = true
) {

    val view = LocalView.current
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        modifier = modifier.heightIn(max = TextFieldDefaults.MinHeight * 6)
    ) {

        item {
            QuerySearch(
                query = query,
                label = queryLabel,
                onQueryChanged = onQueryChanged,
                onDoneActionClick = {
                    view.clearFocus()
                    onDoneActionClick()
                },
                onClearClick = {
                    onClearClick()
                }
            )
        }

        if (predictions.isNotEmpty() && isDropdownVisible) {
            items(predictions) { prediction ->
                Row(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            view.clearFocus()
                            onItemClick(prediction)
                        }
                ) {
                    itemContent(prediction)
                }
            }
        }
    }
}

//Pulls the list of names from the FoodDataResponse and returns them as a list of strings
@Composable
fun QuerySearch(
    modifier: Modifier = Modifier,
    query: String,
    label: String,
    onDoneActionClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onQueryChanged: (String) -> Unit
) {
    var showClearButton by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                showClearButton = (focusState.isFocused)
            },
        value = query,
        onValueChange = onQueryChanged,
        label = { Text(text = label) },
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        trailingIcon = {
            if (showClearButton) {
                IconButton(onClick = { onClearClick() }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Clear")
                }
            }

        },
        keyboardActions = KeyboardActions(onDone = {
            onDoneActionClick()
        }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        )
    )
}

//Creates text fields for the user to input data
@Composable
fun InputField(
    label: String,
    state: MutableState<TextFieldValue>,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onlyNum: Boolean = false,
    onlyLet: Boolean = false,
    categories: List<String> = emptyList(),
    onClick: (() -> Unit)? = null,
    onValueChanged: ((TextFieldValue) -> Unit)? = null
) {
    var selectedIndex by remember { mutableStateOf(-1) }

    Column(modifier) {
        if (categories.isNotEmpty()) {
            var expanded by remember { mutableStateOf(false) }

            Box(Modifier.clickable(onClick = { expanded = true })) {
                OutlinedTextField(
                    value = if (selectedIndex >= 0) state.value else TextFieldValue(""),
                    onValueChange = { newValue ->
                        if (onlyNum && newValue.text.any { !it.isDigit() }) return@OutlinedTextField
                        if (onlyLet && newValue.text.any { !it.isLetter() && it != ' ' }) return@OutlinedTextField
                        state.value = newValue
                        onValueChanged?.invoke(newValue)
                    },
                    label = { Text(label) },
                    modifier = Modifier.fillMaxWidth(),
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
                Box(
                    Modifier
                        .clickable(onClick = onClick)
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = state.value,
                        onValueChange = { newValue ->
                            if (onlyNum && newValue.text.any { !it.isDigit() }) return@OutlinedTextField
                            if (onlyLet && newValue.text.any { !it.isLetter() && it != ' ' }) return@OutlinedTextField
                            state.value = newValue
                            onValueChanged?.invoke(newValue)
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
                        onValueChanged?.invoke(newValue)
                    },
                    label = { Text(label) },
                    modifier = Modifier.fillMaxWidth(),
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
    val expirationDate: String,
    val calories: Double?,
    val protein: Double?,
    val fat: Double?,
    val carbs: Double?,
    val fiber: Double?
)

fun Double.roundToNearestTens(): Double {
    return (this / 10).roundToInt() * 10.toDouble()
}

@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    AddScreenPreview()
}