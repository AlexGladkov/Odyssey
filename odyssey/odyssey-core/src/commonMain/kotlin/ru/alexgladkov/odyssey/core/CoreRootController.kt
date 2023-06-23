package ru.alexgladkov.odyssey.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.alexgladkov.odyssey.core.breadcrumbs.Breadcrumb
import ru.alexgladkov.odyssey.core.configuration.RootControllerType
import ru.alexgladkov.odyssey.core.extensions.createUniqueKey
import kotlin.jvm.JvmStatic

abstract class CoreRootController<T : CoreScreen>(
    val rootControllerType: RootControllerType
) {

    abstract var onScreenNavigate: ((Breadcrumb) -> Unit)?
    abstract var onScreenRemove: (CoreScreen) -> Unit
    protected abstract val _backstack: MutableList<T>

    protected val _currentScreen: MutableStateFlow<NavConfiguration?> = MutableStateFlow(null)
    var currentScreen: StateFlow<NavConfiguration?> = _currentScreen.asStateFlow()

    protected fun cleanRealKeyFromType(realKey: String): String =
        realKey
            .replace(flowKey, "")
            .replace(multiStackKey, "")
            .replace("$", "")

    open fun popBackStack(byBackPressed: Boolean = false): T? {
        if (_backstack.isEmpty() || _backstack.size == 1) return null

        val removedObject = _backstack.removeLast()
        onScreenRemove.invoke(removedObject)
        return removedObject
    }

    open fun backToScreen(screenName: String) {
        if (_backstack.isEmpty()) return

        do {
            val removedObject = _backstack.removeLast()
            onScreenRemove.invoke(removedObject)
        } while (_backstack.last().realKey != screenName)
    }

    protected abstract fun pushToStack(value: T)

    companion object {
        const val flowKey = "odyssey_flow_reserved_type"
        const val multiStackKey = "odyssey_multi_stack_reserved_type"

        @JvmStatic
        fun randomizeKey(key: String): String = createUniqueKey(key)
    }
}