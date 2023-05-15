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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
        items.forEach { item ->
            ItemCard(item = item)
        }
    }

}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun ItemCard(item: Item) {
    val colors = listOf(
        Color.Blue,
        Color.Green,
        Color.Red,
        // Add more colors for each category
    )

    val categoryColor = colors[item.category.hashCode() % colors.size]

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
                // Left side color indicator strip
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(leftIndicatorHeight)
                        .background(categoryColor)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Middle section: Name
                Text(
                    text = item.name,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Right side: Days left until expiration
            Text(
                text = "Days left: ${calculateDaysLeft(item.expirationDate)}",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            )

            AnimatedVisibility(visible = expanded) {
                Spacer(modifier = Modifier.height(4.dp))

                // Additional information about the item
                Column {
                    Text("Calories: ${item.calories ?: "-"}")
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