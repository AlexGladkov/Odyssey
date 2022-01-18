package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.odyssey.compose.controllers.ModalSheetConfiguration
import ru.alexgladkov.odyssey.compose.local.LocalModalSheetController
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun FeedScreen() {
    val modalSheetController = LocalModalSheetController.current

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column {
            Text("Feed Screen", fontSize = 24.sp)
            Text(modifier = Modifier.clickable {
                val modalSheetConfiguration = ModalSheetConfiguration(peekHeight = 700, cornerRadius = 16)
                modalSheetController.presentNew(modalSheetConfiguration) {
                    StoresScreen(username = "Alex Gladkov")
                }
            }.padding(top = 16.dp), text = "Change screen")
        }
    }
}