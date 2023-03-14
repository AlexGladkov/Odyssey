package ru.alexgladkov.odyssey.compose.setup

import androidx.compose.runtime.Composable
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

expect fun setNavigationContent(configuration: OdysseyConfiguration, onApplicationFinish: () -> Unit, navigationGraph: @Composable RootComposeBuilder.() -> Unit)