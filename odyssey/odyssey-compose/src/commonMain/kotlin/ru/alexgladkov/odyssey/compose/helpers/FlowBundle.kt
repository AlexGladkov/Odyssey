package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.runtime.Immutable
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.ScreenType
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController

@Immutable
data class FlowBundle(
    val key: String = "",
    val params: Any? = null,
    val startScreen: String,
    val rootController: RootController
)

@Immutable
data class MultiStackBundle(
    val startScreen: String?,
    val rootController: MultiStackRootController,
    val params: Any? = null
)

@Immutable
data class BottomSheetBundle(
    val key: String,
    val currentKey: String,
    val params: Any? = null
)