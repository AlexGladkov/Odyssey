package ru.alexgladkov.hilt_demo.ui

import androidx.compose.foundation.layout.Row
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
    Label("screen 1\nrandom number - ${viewModel.randomLifecycleValue}")
}

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

    Label("screen 2\nrandom number - ${viewModel.randomLifecycleValue}")
}

@Composable
fun HiltScreenThree() {
    val viewModel: ViewModelTwo = hiltViewModel()
    val rootController = LocalRootController.current
    Row {
        TextButton(
            onClick = {
                println("Back to one clicked from 3")
                rootController.backToScreen("one")
                //rootController.popBackStack()
            }
        ) {
            Text("Back to one")
        }
        TextButton(
            onClick = {
                rootController.push("four")
            }
        ) {
            Text("Push")
        }
    }
    Label("screen 3\nrandom number - ${viewModel.randomLifecycleValue}")
}

@Composable
fun HiltScreenFour() {
    val viewModel: ViewModelTwo = hiltViewModel()
    val rootController = LocalRootController.current

    Row {
        TextButton(
            onClick = {
                println("Back to one clicked from 4")
                rootController.backToScreen("one")
            }
        ) {
            Text("Back to one")
        }
        TextButton(
            onClick = {
                rootController.push("four")
            }
        ) {
            Text("Push")
        }
    }
    Label("screen 4\nrandom number - ${viewModel.randomLifecycleValue}")
}