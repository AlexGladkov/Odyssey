package ru.alexgladkov.odyssey.compose.navigation

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.ScreenType
import ru.alexgladkov.odyssey.compose.helpers.FlowBuilderModel
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.MultiStackBuilderModel
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.MultiStackConfiguration
import ru.alexgladkov.odyssey.core.configuration.RootControllerType

/**
 * Base builder, declarative helper for navigation graph builder
 * @see RootController
 */
class RootComposeBuilder {
    private val _screens: MutableList<AllowedDestination> = mutableListOf()
    private val _screenMap: HashMap<String, RenderWithParams<Any?>> = hashMapOf()

    internal fun addScreen(
        key: String,
        screenMap: ImmutableMap<String, RenderWithParams<Any?>>,
    ) {
        _screens.add(AllowedDestination(key = key, screenType = ScreenType.Simple))
        _screenMap.putAll(screenMap)
    }

    fun addFlow(
        key: String,
        flowBuilderModel: FlowBuilderModel
    ) {
        _screens.add(
            AllowedDestination(
                key = key,
                screenType = ScreenType.Flow(flowBuilderModel = flowBuilderModel)
            )
        )
    }

    fun addMultiStack(
        key: String,
        displayConfiguration: MultiStackConfiguration,
        multiStackBuilderModel: MultiStackBuilderModel
    ) {
        _screens.add(
            AllowedDestination(
                key = key,
                screenType = ScreenType.MultiStack(multiStackBuilderModel, displayConfiguration)
            )
        )
    }

    fun build(): RootController = RootController(RootControllerType.Root)
        .apply {
            updateScreenMap(_screenMap.toImmutableMap())
            setNavigationGraph(_screens.toImmutableList())
        }
}