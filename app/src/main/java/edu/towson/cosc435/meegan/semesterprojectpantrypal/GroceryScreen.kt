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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

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

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            OutlinedTextField(
                value = newItemText.value,
                onValueChange = { newItemText.value = it },
                label = { Text("Add item to Grocery list") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                leadingIcon = {
                    Icon(Icons.Default.Add, contentDescription = "Add Icon")
                },
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
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(Color.Transparent, shape = RoundedCornerShape(7.dp))
                            .padding(4.dp)
                            .shadow(elevation = 5.dp, shape = RoundedCornerShape(7.dp))
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(bottomStart = 7.dp, bottomEnd = 7.dp, topStart = 7.dp, topEnd = 7.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .background(Color.White, shape = RoundedCornerShape(7.dp))
                                .padding(1.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Checkbox(
                                checked = checkedStates[item] == true,
                                onCheckedChange = { checked ->
                                    checkedStates[item] = checked
                                },
                                modifier = Modifier.padding(end = 4.dp),
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
                                textDecoration = if (checkedStates[item] == true) TextDecoration.LineThrough else TextDecoration.None,
                                color = if (checkedStates[item] == true) Color.Gray else Color.Black
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

            Spacer(modifier = Modifier.height(16.dp))

            if (groceryItems.isNotEmpty()) {
                Button(
                    onClick = {
                        val crossedOutItems = checkedStates.filterValues { it }.keys.toList()
                        crossedOutItems.forEach {
                            checkedStates.remove(it)
                            groceryItems.remove(it)
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Clear Crossed-out Items")
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

