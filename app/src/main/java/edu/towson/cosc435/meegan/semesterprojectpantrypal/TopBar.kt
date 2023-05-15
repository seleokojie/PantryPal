
package edu.towson.cosc435.meegan.semesterprojectpantrypal

import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// TopBar Composable function that displays a centered title "Add New Item", only shows on Add route
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
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        backgroundColor = Color.Transparent,
        contentColor = Color.Black,
    )
}

@Composable
fun SearchBar(label: String, hintText: String, backgroundColor: Color = Color.White) {
    OutlinedTextField(
        value = "",
        onValueChange = { },
        label = { Text(label) },
        placeholder = { Text(hintText) },
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(percent = 50))
            .padding(horizontal = 16.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        },
    )
}


// Preview function for TopBar
@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}
