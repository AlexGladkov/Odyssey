package ru.alexgladkov.common.compose.sample_lmwork.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.alexgladkov.common.compose.sample_lmwork.NavigationTree
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun AuthScreen() {
    val rootController = LocalRootController.current

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier.fillMaxWidth().align(Alignment.Center).padding(16.dp),
            onClick = {
                rootController.push(NavigationTree.Auth.LoginWeb.name)
            }) {
            Text("Login")
        }
    }
}