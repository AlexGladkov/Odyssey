package ru.alexgladkov.odyssey.core.navigation

import ru.alexgladkov.odyssey.core.AllowedDestination
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.RenderWithParams
import ru.alexgladkov.odyssey.core.ScreenType
import ru.alexgladkov.odyssey.core.helpers.FlowBuilderModel
import ru.alexgladkov.odyssey.core.navigation.bottom_bar_navigation.BottomNavModel
import ru.alexgladkov.odyssey.core.navigation.bottom_bar_navigation.MultiStackBuilderModel

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