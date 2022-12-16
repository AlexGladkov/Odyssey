package ru.alexgladkov.odyssey.core

import ru.alexgladkov.odyssey.core.breadcrumbs.Breadcrumb

abstract class CoreRootController<T> {
    abstract var onScreenNavigate: ((Breadcrumb) -> Unit)?
    protected abstract val _backstack: MutableList<T>

    protected fun cleanRealKeyFromType(realKey: String): String =
        realKey
            .replace(flowKey, "")
            .replace(multiStackKey, "")
            .replace("$", "")

    companion object {
        const val flowKey = "odyssey_flow_reserved_type"
        const val multiStackKey = "odyssey_multi_stack_reserved_type"
    }
}