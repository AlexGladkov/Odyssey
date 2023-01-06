package ru.alexgladkov.hilt_demo.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun HiltScreenTwo() {
    val viewModel: ViewModelTwo = hiltViewModel()
    val rootController = LocalRootController.current
    Row {
        TextButton(
            onClick = {
                rootController.popBackStack()
            }
        ) {
            Text("Back")
        }
        TextButton(
            onClick = {
                rootController.push("three")
            }
        ) {
            Text("Push")
        }
    }

    NumberLabel(viewModel.randomLifecycleValue)
}