package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
fun OnboardingScreen(count: Int) {
    val rootController = LocalRootController.current
    var textFieldState by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        if (count == 4) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Onboarding Finished",
                fontSize = 24.sp, fontWeight = FontWeight.Medium
            )

            Button(modifier = Modifier.padding(16.dp).fillMaxWidth()
                .align(Alignment.BottomCenter), onClick = {
                rootController.findRootController()
                    .present(screen = "main", launchFlag = LaunchFlag.SingleNewTask)
            }) {
                Text("Open App")
            }
        } else {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Onboarding Screen $count",
                    fontSize = 24.sp, fontWeight = FontWeight.Medium
                )

                TextField(modifier = Modifier.padding(16.dp),
                    value = textFieldState, onValueChange = {
                        textFieldState = it
                    })
            }

            Button(modifier = Modifier.padding(16.dp).fillMaxWidth()
                .align(Alignment.BottomCenter), onClick = {
                rootController.push(screen = "onboarding", params = count + 1)
            }) {
                Text("Show next")
            }
        }
    }
}