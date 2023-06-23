package ru.alexgladkov.odyssey

import ru.alexgladkov.odyssey.core.CoreRootController
import ru.alexgladkov.odyssey.core.CoreScreen
import ru.alexgladkov.odyssey.core.breadcrumbs.Breadcrumb
import ru.alexgladkov.odyssey.core.configuration.RootControllerType

data class TestScreen(override val key: String) : CoreScreen

class TestCoreRootController: CoreRootController<TestScreen>(rootControllerType = RootControllerType.Root) {

    override var onScreenNavigate: ((Breadcrumb) -> Unit)? = null
    override var onScreenRemove: (TestScreen) -> Unit = {}
    override val _backstack: MutableList<TestScreen> = mutableListOf()

    fun launch(screen: TestScreen) {
        pushToStack(screen)
    }

    override fun pushToStack(value: TestScreen) {
        _backstack.add(value)
    }
}