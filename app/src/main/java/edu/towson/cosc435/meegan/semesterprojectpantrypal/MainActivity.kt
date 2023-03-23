package edu.towson.cosc435.meegan.semesterprojectpantrypal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.towson.cosc435.meegan.semesterprojectpantrypal.ui.theme.SemesterProjectPantryPalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SemesterProjectPantryPalTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Add your content here
                        StickyBottomNavigationBar()
                    }
                }
            }
        }
    }
}

@Composable
fun StickyBottomNavigationBar() {
    BottomAppBar(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(),
        backgroundColor = Color(34, 132, 4),
        cutoutShape = CircleShape,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                BottomNavigationIcon("Home", Icons.Default.Home)
                BottomNavigationIcon("Grocery", Icons.Default.ShoppingCart)
                BottomNavigationIcon("Add", Icons.Default.Add)
                BottomNavigationIcon("Settings", Icons.Default.Settings)
            }
        }
    )
}


@Composable
fun BottomNavigationIcon(label: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = { /* Handle icon button click */ }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.caption,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun PreviewStickyBottomNavigationBar() {
    StickyBottomNavigationBar(

    )
}
