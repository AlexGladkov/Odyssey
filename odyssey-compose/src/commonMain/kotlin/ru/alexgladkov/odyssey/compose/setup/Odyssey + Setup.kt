package ru.alexgladkov.odyssey.compose.setup

import androidx.compose.runtime.Composable
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

@Composable
expect fun setNavigationContent(configuration: OdysseyConfiguration, onApplicationFinish: () -> Unit, navigationGraph: RootComposeBuilder.() -> Unit)