package ru.alexgladkov.odyssey.compose.setup

import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

expect fun setNavigationContent(configuration: OdysseyConfiguration, onApplicationFinish: () -> Unit, navigationGraph: RootComposeBuilder.() -> Unit)