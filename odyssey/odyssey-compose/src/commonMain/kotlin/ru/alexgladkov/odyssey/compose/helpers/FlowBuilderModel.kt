package ru.alexgladkov.odyssey.compose.helpers

import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.Render
import ru.alexgladkov.odyssey.compose.ScreenType

data class FlowBuilderModel(
    val key: String,
    val allowedDestination: List<AllowedDestination>,
    val screenMap: HashMap<String, Render<Any?>>
)

class FlowBuilder(val name: String) {
    private val _screenMap: HashMap<String, Render<Any?>> = hashMapOf()
    private val _allowedDestinations: MutableList<AllowedDestination> = mutableListOf()

    fun addScreen(name: String, content: Render<Any?>) {
        _allowedDestinations.add(AllowedDestination(key = name, screenType = ScreenType.Simple))
        _screenMap[name] = content
    }

    fun build(): FlowBuilderModel {
        return FlowBuilderModel(
            key = name,
            screenMap = _screenMap,
            allowedDestination = _allowedDestinations
        )
    }
}

fun FlowBuilder.screen(name: String, content: Render<Any?>) {
    addScreen(name = name, content = content)
}