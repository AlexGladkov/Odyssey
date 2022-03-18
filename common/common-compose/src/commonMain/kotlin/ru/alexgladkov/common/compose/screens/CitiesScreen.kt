package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import ru.alexgladkov.common.compose.helpers.generateCities
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
fun CitiesScreen() {
    val cities by remember { mutableStateOf(generateCities(40)) }
    val rootController = LocalRootController.current

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Choose your city",
            fontSize = 20.sp, fontWeight = FontWeight.Medium
        )

        LazyColumn {
            cities.forEach {
                item {
                    Text(modifier = Modifier.clickable {
                        rootController.present(
                            screen = "main",
                            startTabPosition = 1,
                            startScreen = "product"
                        )
                    }.fillMaxWidth().padding(16.dp), text = it.title)
                }
            }
        }
    }
}