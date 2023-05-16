package edu.towson.cosc435.meegan.semesterprojectpantrypal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.towson.cosc435.meegan.semesterprojectpantrypal.ui.theme.lightGreen
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen() {
    val items = remember { AppState.items }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        if (items.isEmpty()) {
            Text(
                text = "No Items in Your Pantry :(",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            ItemList(items = items)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun ItemList(items: List<Item>) {
    LazyColumn(
        modifier = Modifier.fillMaxHeight()
    ) {
        items(items = items) { item ->
            ItemCard(item = item)
        }
    }
}

@Composable
fun ItemCard(item: Item) {
    val categoryColor = mapCategoryToColor(item.category)
    var expanded by remember { mutableStateOf(false) }
    val borderStrokeWidth = if (expanded) 2.dp else 0.dp
    val borderColor = if (expanded) lightGreen else Color.Transparent
    val leftIndicatorHeight by animateDpAsState(if (expanded) 60.dp else 30.dp)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp,
        border = BorderStroke(borderStrokeWidth, borderColor)
    ) {
        Column(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(leftIndicatorHeight)
                        .background(categoryColor)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.name,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Qty: ${item.quantity}",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Days left: ${calculateDaysLeft(item.expirationDate)}",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            )

            AnimatedVisibility(visible = expanded) {
                Spacer(modifier = Modifier.height(4.dp))
                Column {
                    Text("Calories: ${item.calories?.toInt() ?: "-"}")
                    Text("Protein: ${item.protein ?: "-"}")
                    Text("Fat: ${item.fat ?: "-"}")
                    Text("Carbs: ${item.carbs ?: "-"}")
                    Text("Fiber: ${item.fiber ?: "-"}")
                }
            }
        }
    }
}

fun calculateDaysLeft(expirationDate: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val currentDate = LocalDate.now()
    val today = dateToInt(currentDate.format(formatter))
    val expiration = dateToInt(expirationDate)
    val daysLeftInt = expiration - today

    return if (daysLeftInt <= 0) {
        "Expired"
    } else {
        daysLeftInt.toString()
    }
}

fun dateToInt(date: String): Long {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val localDate = LocalDate.parse(date, formatter)
    return localDate.toEpochDay()
}
fun mapCategoryToColor(category: String): Color {
    return when (category) {
        "Baking Ingredients and Supplies" -> Color(0xFFFF9800) // Orange
        "Beans, Peas, and Lentils" -> Color(0xFF9C27B0) // Purple
        "Beverages" -> Color(0xFF00BCD4) // Cyan
        "Breakfast Foods" -> Color(0xFF8BC34A) // Light Green
        "Canned Goods" -> Color(0xFFFF5722) // Deep Orange
        "Cheese" -> Color(0xFF795548) // Brown
        "Condiments" -> Color(0xFF9E9E9E) // Grey
        "Dairy" -> Color(0xFFFF9800) // Orange
        "Dessert" -> Color(0xFFE91E63) // Pink
        "Eggs" -> Color(0xFF8BC34A) // Light Green
        "Frozen Foods" -> Color(0xFF9C27B0) // Purple
        "Fruit" -> Color(0xFFE57373) // Red
        "Grains (Refined)" -> Color(0xFF607D8B) // Blue Gray
        "Grains (Whole)" -> Color(0xFF795548) // Brown
        "Juice" -> Color(0xFFFF8A65) // Orange
        "Legumes" -> Color(0xFF4CAF50) // Green
        "Meats (Processed)" -> Color(0xFFE91E63) // Pink
        "Meats (Red)" -> Color(0xFFF44336) // Red
        "Meats (White)" -> Color(0xFF9E9E9E) // Grey
        "Non-Dairy Calcium Alternatives" -> Color(0xFFFF5722) // Deep Orange
        "Nuts and Seeds" -> Color(0xFFCDDC39) // Lime
        "Oils" -> Color(0xFF9E9E9E) // Grey
        "Other" -> Color(0xFF9E9E9E) // Grey
        "Pasta" -> Color(0xFF607D8B) // Blue Gray
        "Poultry" -> Color(0xFF03A9F4) // Light Blue
        "Sauce" -> Color(0xFF8BC34A) // Light Green
        "Seafood" -> Color(0xFF00BCD4) // Cyan
        "Snacks and Treats" -> Color(0xFFCDDC39) // Lime
        "Soda" -> Color(0xFFFFFF00) // Yellow
        "Soup" -> Color(0xFFDBCDA6) // Tan
        "Soy Products" -> Color(0xFFFFC107) // Amber
        "Spices and Herbs" -> Color(0xFF795548) // Brown
        "Vegetables (Dark-Green)" -> Color(0xFF4CAF50) // Green
        "Vegetables (Red and Orange)" -> Color(0xFFFF5722) // Deep Orange
        "Vegetables (Starchy)" -> Color(0xFFFFEB3B) // Yellow
        "Vegetables (Other)" -> Color(0xFF2196F3) // Blue
        "Yogurt" -> Color(0xFF607D8B) // Blue Gray
        else -> Color.Black // Default color if category is not found
    }
}