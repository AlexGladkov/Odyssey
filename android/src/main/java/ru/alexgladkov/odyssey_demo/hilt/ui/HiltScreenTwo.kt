package ru.alexgladkov.odyssey_demo.hilt.ui

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey_demo.hilt.viewModel.ViewModelTwo

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
