package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.RootController

@Composable
fun SplashScreen(rootController: RootController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(24.dp),
            text = "Splash Screen", fontWeight = FontWeight.Medium, fontSize = 28.sp,
            color = Color.Black
        )

        Column(modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) {
            Button(onClick = {
                rootController.launch(NavigationTree.Root.Auth.toString(), params = "Splash", LaunchFlag.SingleNewTask)
            }) {
                Text("Go to Auth Screen")
            }
        }
    }
}