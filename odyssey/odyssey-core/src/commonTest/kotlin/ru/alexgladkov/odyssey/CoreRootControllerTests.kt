package ru.alexgladkov.odyssey

import kotlin.test.Test
import kotlin.test.assertEquals

class CoreRootControllerTests {

    @Test
    fun testOnScreeRemoveCorrectBackWork() {
        val coreRootController = TestCoreRootController()
        coreRootController.launch(TestScreen("screen 1"))
        coreRootController.launch(TestScreen("screen 2"))
        coreRootController.launch(TestScreen("screen 3"))
        var result = 0

        coreRootController.onScreenRemove = {
            if ("screen 3" == it.key) result += 1
        }

        coreRootController.popBackStack()

        coreRootController.onScreenRemove = {
            if ("screen 2" == it.key) result += 1
        }

        coreRootController.popBackStack()

        coreRootController.onScreenRemove = {
            if ("screen 1" == it.key) result += 1
        }

        coreRootController.popBackStack()
        assertEquals(result, 3)
    }

    @Test
    fun testBackToScreenFunction() {
        val coreRootController = TestCoreRootController()
        coreRootController.launch(TestScreen("screen 1"))
        coreRootController.launch(TestScreen("screen 2"))
        coreRootController.launch(TestScreen("screen 3"))
        coreRootController.launch(TestScreen("screen 4"))
        coreRootController.launch(TestScreen("screen 5"))
        coreRootController.launch(TestScreen("screen 6"))
        var result = 0

        coreRootController.onScreenRemove = {
            if (it.key == "screen 6") result += 1
            if (it.key == "screen 5") result += 1
            if (it.key == "screen 4") result += 1
            if (it.key == "screen 3") result += 1
        }

        coreRootController.backToScreen("screen 2")
        assertEquals(result, 4)
    }
}