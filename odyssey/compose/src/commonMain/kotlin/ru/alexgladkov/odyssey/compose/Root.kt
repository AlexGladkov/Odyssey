package ru.alexgladkov.odyssey.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ru.alexgladkov.odyssey.core.RootController

@Composable
fun rememberRootController(parentRoot: RootController? = null): RootController {
    val rootController = RootController()
    rootController.parentRoot = parentRoot
    parentRoot?.let {
        it.childRoot = rootController
    }
    return remember { rootController }
}