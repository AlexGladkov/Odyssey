package ru.alexgladkov.odyssey.compose.helpers

import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.ScreenType
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController

data class FlowBundle(
    val key: String = "",
    val params: Any? = null,
    val startScreen: String,
    val rootController: RootController
)

data class MultiStackBundle(
    val startScreen: String?,
    val rootController: MultiStackRootController
)

data class DeepLinkBundle(
    val screen: String,
    val startScreen: String,
    val type: ScreenType
)

data class BottomSheetBundle(
    val key: String,
    val currentKey: String,
    val params: Any? = null
)