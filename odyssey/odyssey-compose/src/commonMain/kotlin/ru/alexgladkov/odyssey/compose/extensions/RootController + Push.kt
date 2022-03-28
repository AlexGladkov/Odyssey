package ru.alexgladkov.odyssey.compose.extensions

import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.animations.defaultPushAnimation

/**
 * Helper function to draw screen with push style (inner navigation)
 * @param screen - screen code, for example "splash". Must be included
 * in navigation graph or will cause an error
 * @param params - any bunch of params you need for the screen
 * @param launchFlag - flag if you want to change default behavior @see LaunchFlag
 */
fun RootController.push(screen: String, params: Any? = null, launchFlag: LaunchFlag? = null) {
    launch(screen = screen, startScreen = null, startTabPosition = 0, params = params, animationType = defaultPushAnimation(), launchFlag)
}

/**
 * Helper function to draw screen with push style (inner navigation)
 * @param screen - screen code, for example "splash". Must be included
 * in navigation graph or will cause an error
 * @param params - any bunch of params you need for the screen
 */
fun RootController.push(screen: String, params: Any? = null) {
    launch(screen = screen, params = params, animationType = defaultPushAnimation())
}
