package ru.alexgladkov.odyssey

import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.navigation.testNavigationGraph
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RootControllerTests {

    private lateinit var rootController: RootController

    @BeforeTest
    fun setupNavigationGraph() {
        rootController = RootComposeBuilder().apply { testNavigationGraph() }.build()
    }

    @Test
    fun testBackToScreenAndScreenRemoveWork() {
        rootController.launch("screen 1")
        rootController.launch("screen 2")
        rootController.launch("screen 3")
        rootController.launch("screen 4")
        rootController.launch("screen 5")
        rootController.launch("screen 6")
        var result = 0

        rootController.onScreenRemove = {
            if (it.realKey == "screen 6") result += 1
            if (it.realKey == "screen 5") result += 1
            if (it.realKey == "screen 4") result += 1
            if (it.realKey == "screen 3") result += 1
        }

        rootController.backToScreen("screen 2")
        assertEquals(result, 4)
    }

    @Test
    fun testSimpleNavigation() {
        rootController.launch("screen 1")
        rootController.launch("screen 2")

        rootController.onScreenRemove = {
            assertEquals(it.realKey, "screen 2")
        }

        rootController.popBackStack()
    }

    @Test
    fun testStartFlowAndBack() {
        rootController.launch("screen 1")
        rootController.launch("flow 1")

        rootController.onScreenRemove = {
            assertEquals(it.realKey?.contains("flow 1"), true)
        }

        rootController.popBackStack()
    }

    @Test
    fun testClearLaunchFlag() {
        rootController.launch("screen 1")
        rootController.launch("screen 2", launchFlag = LaunchFlag.SingleNewTask)

        rootController.onApplicationFinish = {
            assertEquals(1, 1)
        }

        rootController.popBackStack()
    }

    @Test
    fun testFlowSimpleNavigationAndBackNavigation() {
        rootController.launch("screen 1")
        rootController.launch("flow 1")
        rootController.launch("screen 7")

        val flowRootController = rootController.childrenRootController.first()
        flowRootController.launch("flow screen 2")
        flowRootController.launch("flow screen 3")

        rootController.onScreenRemove = {
            assertEquals(it.realKey?.contains("screen 7"), true)
        }

        rootController.popBackStack()

        rootController.onScreenRemove = {
            assertEquals(it.realKey?.contains("odyssey_flow_reserved_type"), true)
        }

        rootController.popBackStack()
    }
}