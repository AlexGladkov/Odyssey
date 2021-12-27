package ru.alexgladkov.odyssey.compose.helpers

import ru.alexgladkov.odyssey.compose.RootController

data class FlowBundle(
    val key: String = "",
    val params: Any? = null,
    val rootController: RootController
)