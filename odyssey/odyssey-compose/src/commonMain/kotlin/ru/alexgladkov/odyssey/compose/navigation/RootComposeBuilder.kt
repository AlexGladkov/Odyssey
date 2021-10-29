package ru.alexgladkov.odyssey.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.helpers.BottomNavigationHost
import ru.alexgladkov.odyssey.compose.helpers.MutableScreenMap
import ru.alexgladkov.odyssey.compose.helpers.ScreenBundle
import ru.alexgladkov.odyssey.compose.helpers.ScreenMap
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.ScreenHost
import ru.alexgladkov.odyssey.core.destination.Destination

class RootComposeBuilder(
    private val screenHost: ScreenHost
) {

    private val _destinations: MutableList<Destination> = mutableListOf()
    private val _screenMap: MutableScreenMap = hashMapOf()

    val screenMap: ScreenMap = _screenMap

    fun addDestination(
        screenMap: ScreenMap,
        destination: Destination
    ) {
        _destinations.add(destination)
        _screenMap.putAll(screenMap)
    }

    fun addScreenValue(
        name: String,
        content: @Composable ScreenBundle.() -> Unit
    ) {
        _screenMap[name] = content
    }

    fun build(): RootController = RootController(screenHost).apply {
        setNavigationGraph(_destinations)
    }
}