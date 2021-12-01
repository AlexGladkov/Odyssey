package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.layout.*
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
import ru.alexgladkov.odyssey.core.animations.AnimationType

@Composable
fun SplashScreen(rootController: RootController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(24.dp),
            text = "Splash Screen", fontWeight = FontWeight.Medium, fontSize = 28.sp,
            color = Color.Black
        )

        Column(modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) {
            Row {
                Button(onClick = {
                    rootController.present(
                        screen = NavigationTree.Root.Auth.toString(),
                        params = "Splash",
                        launchFlag = LaunchFlag.SingleNewTask
                    )
                }) {
                    Text("Auth With No History")
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = {
                    rootController.present(
                        screen = NavigationTree.Root.Auth.toString(),
                        params = "Splash",
                    )
                }) {
                    Text("Auth With History")
                }
            }

        }
    }
}