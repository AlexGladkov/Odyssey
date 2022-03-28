package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation.ModalSheetConfiguration

@Composable
fun StoresScreen(username: String) {
    val modalSheetController = LocalRootController.current.findModalController()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(modifier = Modifier.weight(1f), text = "Stores for $username", fontSize = 20.sp, color = Color.Black)
            Text(
                modifier = Modifier.clickable {
                    modalSheetController.removeTopScreen()
                },
                text = "Close", fontSize = 14.sp
            )
        }


        LazyColumn(modifier = Modifier.fillMaxSize()) {
            for (i in 0..20) {
                item {
                    Text(modifier = Modifier.clickable {
                        val modalSheetConfiguration = ModalSheetConfiguration(maxHeight = null, cornerRadius = 16)
                        modalSheetController.presentNew(modalSheetConfiguration) {
                            DepartmentScreen(storeId = i)
                        }
                    }.fillMaxWidth().padding(16.dp), text = "Store $i")
                }
            }
        }
    }
}