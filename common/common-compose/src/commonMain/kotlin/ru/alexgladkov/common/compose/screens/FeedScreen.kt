package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation.AlertConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation.CustomModalConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation.ModalSheetConfiguration

@Composable
fun FeedScreen() {
    val modalController = LocalRootController.current.findModalController()

    Box(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            Text("Feed Screen", fontSize = 24.sp)
            Text(modifier = Modifier.clickable {
                val modalSheetConfiguration =
                    ModalSheetConfiguration(maxHeight = 0.7f, cornerRadius = 16)
                modalController.present(modalSheetConfiguration) {
                    StoresScreen(username = "Alex Gladkov")
                }
            }.padding(top = 16.dp), text = "Change screen")

            Text(modifier = Modifier.clickable {
                val modalSheetConfiguration = CustomModalConfiguration
                modalController.present(modalSheetConfiguration) {
                    CustomRenderModalScreen(username = "Artem")
                }
            }.padding(top = 16.dp), text = "Custom modal render screen")

            Text(modifier = Modifier.clickable {
                val modalSheetConfiguration =
                    ModalSheetConfiguration(maxHeight = 0.7f, cornerRadius = 16,
                        backContent = {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Screamer(alpha = 0.2f) {
                                    modalController.removeTopScreen()
                                }
                                Box(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .background(color = Color.White),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(text = "Some message for modal")
                                }
                            }
                        }
                    )
                modalController.present(modalSheetConfiguration) {
                    StoresScreen(username = "Artem")
                }
            }.padding(top = 16.dp), text = "Custom back content render screen")

            Text(modifier = Modifier.clickable {
                val alertConfiguration =
                    AlertConfiguration(maxHeight = 0.7f, maxWidth = 0.8f, cornerRadius = 4)
                modalController.present(alertConfiguration) {
                    StoresScreen(username = "Artem")
                }
            }.padding(top = 16.dp), text = "AlertDialog render screen")
        }
    }
}