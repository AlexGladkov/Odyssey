package ru.alexgladkov.hilt_demo.ui

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun HiltScreenOne() {
    val viewModel: ViewModelOne = hiltViewModel()
    val rootController = LocalRootController.current
    TextButton(
        onClick = {
            rootController.push("two")
        }
    ) {
        Text("Push")
    }
    NumberLabel(viewModel.randomLifecycleValue)
}