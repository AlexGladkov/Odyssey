package ru.alexgladkov.hilt_demo.ui

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun HiltScreenTwo() {
    val viewModel: ViewModelTwo = hiltViewModel()
    val rootController = LocalRootController.current
    TextButton(
        onClick = {
            rootController.popBackStack()
        }
    ) {
        Text("Back")
    }
    NumberLabel(viewModel.randomLifecycleValue)
}