package edu.towson.cosc435.meegan.semesterprojectpantrypal

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen() {
    val items = remember { AppState.items }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background("#e3ffde".toColor())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items.forEach { item ->
                /*item {
                    ItemCard(item)
                }*/
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

                            //"Name: ${item.name}\nCategory: ${item.category}\nQuantity: ${item.quantity}\nEXP: ${item.expirationDate}\nCalories: ${item.calories}\nProtein: ${item.protein}\nFat: ${item.fat}\nCarbs: ${item.carbs}\nFiber: ${item.fiber}",
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

}

/*@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemCard(item: Item) {
    val categories = listOf(
        "Fruit",
        "Fruit Juice",
        "Dark-Green Vegetables",
        "Red and Orange Vegetables",
        "Beans, Peas, and Lentils",
        "Starchy Vegetables",
        "Other Vegetables",
        "Whole Grains",
        "Refined Grains",
        "Meats",
        "Poultry",
        "Seafood",
        "Eggs",
        "Nuts and Seeds",
        "Soy Products",
        "Milk",
        "Non-Dairy Calcium Alternatives",
        "Yogurt",
        "Cheese"
    )

    val daysLeft = calculateDaysLeft(item.expirationDate)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            // Left Side: Category Color
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(getCategoryColor(item.category, categories))
            )

            // Middle Section: Food Name
            Text(
                text = item.name,
                modifier = Modifier.padding(start = 16.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )

            // Right Side: Days Left
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Expires in $daysLeft days",
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
private fun getCategoryColor(category: String, categories: List<String>): Color {
    val categoryIndex = categories.indexOf(category)
    val colors = listOf(
        Color.Red,
        Color.Green,
        Color.Blue,
        // Add more colors as needed
    )
    return colors.getOrElse(categoryIndex) { Color.Gray }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun calculateDaysLeft(expirationDate: String): Int {
    val currentDate = LocalDate.now()
    val expirationLocalDate = LocalDate.parse(expirationDate, DateTimeFormatter.ISO_DATE)
    return ChronoUnit.DAYS.between(currentDate, expirationLocalDate).toInt()
}*/

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}