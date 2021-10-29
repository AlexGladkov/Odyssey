package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.odyssey.core.RootController

@Composable
fun FeedScreen(rootController: RootController) {
    val paramState = remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "Feed Screen", fontWeight = FontWeight.Medium, fontSize = 28.sp,
                color = Color.Black
            )

            TextField(
                modifier = Modifier.padding(top = 8.dp),
                value = paramState.value,
                onValueChange = {
                    paramState.value = it
                },
                placeholder = {
                    Text("Enter param to test navigation")
                }
            )
        }

        Row(modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) {
            Button(onClick = {
                rootController.launch(NavigationTree.Main.Detail.toString(), DetailParams(paramState.value))
            }) {
                Text("Go to Detail")
            }
        }
    }
}