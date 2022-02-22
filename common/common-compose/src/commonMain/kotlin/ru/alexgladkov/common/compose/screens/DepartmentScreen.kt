package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.odyssey.core.local.LocalRootController

@Composable
fun DepartmentScreen(storeId: Int) {
    val modalSheetController = LocalRootController.current.findModalSheetController()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Departments for $storeId store", fontSize = 20.sp, color = Color.Black)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            for (i in 0..20) {
                item {
                    Text(modifier = Modifier.clickable {
                        modalSheetController.removeTopScreen()
                    }.fillMaxWidth().padding(16.dp), text = "Department $i")
                }
            }
        }
    }
}