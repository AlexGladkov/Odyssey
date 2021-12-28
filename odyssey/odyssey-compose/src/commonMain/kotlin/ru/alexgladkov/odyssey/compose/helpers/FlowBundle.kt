package ru.alexgladkov.odyssey.compose.helpers

import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.navigation.TabItem

data class FlowBundle(
    val key: String = "",
    val params: Any? = null,
    val rootController: RootController
)

data class MultiStackBundle(
    val rootController: MultiStackRootController
)