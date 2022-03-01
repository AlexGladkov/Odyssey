package ru.alexgladkov.common.root.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.common.root.helpers.generateProducts
import ru.alexgladkov.odyssey.core.local.LocalRootController

@Composable
fun ProductScreen() {
    val rootController = LocalRootController.current
    var count by rememberSaveable { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Product $count", fontSize = 24.sp, fontWeight = FontWeight.Medium)

        Row {
            Button(modifier = Modifier.padding(start = 16.dp), onClick = {
                count++
            }) {
                Text("Increase")
            }

            Button(modifier = Modifier.padding(start = 16.dp), onClick = {
                rootController.popBackStack()
            }) {
                Text("Go back")
            }
        }

        Text(
            modifier = Modifier.padding(16.dp),
            text = "Similar products",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        LazyColumn {
            generateProducts(10).forEach {
                item {
                    Text(
                        modifier = Modifier.clickable {
                            rootController.push(screen = "product")
                        }.fillMaxSize().padding(16.dp),
                        text = it.title
                    )
                }
            }
        }
    }
}