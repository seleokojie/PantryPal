package edu.towson.cosc435.meegan.semesterprojectpantrypal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Add New Item",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold )
            }
        },
        backgroundColor = "#75b37c".toColor(),
        contentColor = Color.Black,
    )
}

@Composable
fun SearchBar(label: String, hintText: String) {
    TextField(
        value = "",
        onValueChange = { },
        label = { Text(label) },
        placeholder = { Text(hintText) },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        }
    )
}


// Preview function for TopBar
@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}