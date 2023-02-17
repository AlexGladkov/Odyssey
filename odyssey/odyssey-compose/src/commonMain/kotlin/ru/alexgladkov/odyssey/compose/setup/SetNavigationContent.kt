package ru.alexgladkov.odyssey.compose.setup

import androidx.compose.runtime.Composable
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

@Composable
fun setNavigationContent(configuration: OdysseyConfiguration, navigationGraph: RootComposeBuilder.() -> Unit) {
    setNavigationContent(configuration, onApplicationFinish = {}, navigationGraph)
}