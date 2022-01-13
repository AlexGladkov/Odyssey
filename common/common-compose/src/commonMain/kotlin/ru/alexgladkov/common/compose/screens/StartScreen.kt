package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
fun StartScreen() {
    val rootController = LocalRootController.current

//    Box(modifier = Modifier.fillMaxSize()) {
//        Text(
//            modifier = Modifier.padding(16.dp),
//            text = "Welcome to our app",
//            fontSize = 24.sp, fontWeight = FontWeight.Medium
//        )
//
//        Button(modifier = Modifier.padding(16.dp).fillMaxWidth()
//            .align(Alignment.BottomCenter), onClick = {
//            rootController.push("cities")
//        }) {
//            Text("Choose your regions")
//        }
//    }

    rootController.present("main", launchFlag = LaunchFlag.SingleNewTask)
}