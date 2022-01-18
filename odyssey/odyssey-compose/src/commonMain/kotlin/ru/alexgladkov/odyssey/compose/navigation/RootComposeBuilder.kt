package ru.alexgladkov.odyssey.compose.navigation

import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.ScreenType
import ru.alexgladkov.odyssey.compose.helpers.FlowBuilderModel
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.MultiStackBuilderModel
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.BottomNavModel

/**
 * Base builder, declarative helper for navigation graph builder
 * @see RootController
 */
class RootComposeBuilder {
    private val _screens: MutableList<AllowedDestination> = mutableListOf()
    private val _screenMap: HashMap<String, RenderWithParams<Any?>> = hashMapOf()

    fun addScreen(
        key: String,
        screenMap: Map<String, RenderWithParams<Any?>>,
    ) {
        _screens.add(AllowedDestination(key = key, screenType = ScreenType.Simple))
        _screenMap.putAll(screenMap)
    }

    fun addModalBottomSheet(
        key: String,
        screenMap: Map<String, RenderWithParams<Any?>>
    ) {
        _screens.add(AllowedDestination(key = key, screenType = ScreenType.BottomSheet))
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
        bottomNavModel: BottomNavModel,
        multiStackBuilderModel: MultiStackBuilderModel
    ) {
        _screens.add(
            AllowedDestination(
                key = key,
                screenType = ScreenType.MultiStack(multiStackBuilderModel, bottomNavModel)
            )
        )
    }

    fun build(): RootController = RootController().apply {
        updateScreenMap(_screenMap)
        setNavigationGraph(_screens)
    }
}