package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.odyssey.compose.extensions.presentDeepLink
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun StartScreen() {
    val rootController = LocalRootController.current
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Welcome to our app",
            fontSize = 24.sp, fontWeight = FontWeight.Medium
        )

        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Button(modifier = Modifier.padding(16.dp).fillMaxWidth()
                , onClick = {
//            // Go To Auth
//            rootController.present("auth")

//             Go To DeepLink
                rootController.presentDeepLink("cities")
            }) {
                Text("Choose your regions")
            }
            Button(modifier = Modifier.padding(16.dp).fillMaxWidth()
                , onClick = {
                    rootController.launch("top")
                }) {
                Text("Top nav screen")
            }
            Button(modifier = Modifier.padding(16.dp).fillMaxWidth()
                , onClick = {
                    rootController.launch("custom")
                }) {
                Text("Custom nav screen")
            }
        }

    }
}