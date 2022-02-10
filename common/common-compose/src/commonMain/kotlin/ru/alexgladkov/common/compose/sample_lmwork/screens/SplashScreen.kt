package ru.alexgladkov.common.compose.sample_lmwork.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.alexgladkov.common.compose.sample_lmwork.NavigationTree
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun SplashScreen() {
    val rootController = LocalRootController.current

    Box(modifier = Modifier.fillMaxSize()) {
        Text(modifier = Modifier.align(Alignment.Center), text = "Splash")
    }

    LaunchedEffect(Unit) {
        rootController.present(screen = NavigationTree.Root.Auth.name)
    }
}