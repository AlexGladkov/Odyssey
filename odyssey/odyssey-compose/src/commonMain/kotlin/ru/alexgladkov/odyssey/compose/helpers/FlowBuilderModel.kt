package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.ScreenType

@Immutable
class FlowBuilderModel internal constructor(
    val key: String,
    val allowedDestination: ImmutableList<AllowedDestination>,
    val screenMap: ImmutableMap<String, RenderWithParams<Any?>>
)

class FlowBuilder(val name: String) {
    private val _screenMap: HashMap<String, RenderWithParams<Any?>> = hashMapOf()
    private val _allowedDestinations: MutableList<AllowedDestination> = mutableListOf()

    fun addScreen(name: String, content: RenderWithParams<Any?>) {
        _allowedDestinations.add(AllowedDestination(key = name, screenType = ScreenType.Simple))
        _screenMap[name] = content
    }

    fun build(): FlowBuilderModel {
        return FlowBuilderModel(
            key = name,
            screenMap = _screenMap.toImmutableMap(),
            allowedDestination = _allowedDestinations.toImmutableList()
        )
    }
}