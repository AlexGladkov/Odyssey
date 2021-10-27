package ru.alexgladkov.odyssey.compose.helpers

import ru.alexgladkov.odyssey.core.RootController

data class ScreenBundle(
    val rootController: RootController,
    val params: Any? = null,
    val screenMap: ScreenMap = hashMapOf()
)