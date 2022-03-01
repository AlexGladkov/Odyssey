package ru.alexgladkov.odyssey.core.helpers

import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.controllers.MultiStackRootController

data class FlowBundle(
    val key: String = "",
    val params: Any? = null,
    val rootController: RootController
)

data class MultiStackBundle(
    val rootController: MultiStackRootController
)

data class BottomSheetBundle(
    val key: String,
    val currentKey: String,
    val params: Any? = null
)