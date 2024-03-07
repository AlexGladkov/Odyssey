package ru.alexgladkov.odyssey.compose.extensions

import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.animations.defaultPresentationAnimation

/**
 * Helper function to draw screen with presentation (aka modal) style
 * @param screen - screen code, for example "splash". Must be included
 * in navigation graph or will cause an error
 * @param startScreen - screen code to star
 * @param params - any bunch of params you need for the screen
 * @param launchFlag - flag if you want to change default behavior @see LaunchFlag
 */
fun RootController.present(screen: String, startTabPosition: Int = 0, startScreen: String? = null, params: Any? = null, launchFlag: LaunchFlag? = null) {
    launch(screen, startScreen, startTabPosition, params, defaultPresentationAnimation(), launchFlag)
}

/**
 * Helper function to draw screen with presentation (aka modal) style
 * @param screen - screen code, for example "splash". Must be included
 * in navigation graph or will cause an error
 * @param params - any bunch of params you need for the screen
 */
fun RootController.present(screen: String, params: Any?) {
    launch(screen = screen, params = params)
}

/**
 * Helper function to draw screen with presentation (aka modal) style
 * @param screen - screen code, for example "splash". Must be included
 * in navigation graph or will cause an error
 * @param params - any bunch of params you need for the screen
 * @param startScreen - screen code to star
 */
fun RootController.present(screen: String, params: Any?, startScreen: String) {
    launch(screen = screen, params = params, startScreen = startScreen)
}

/**
 * Helper function to draw screen with presentation (aka modal) style
 * @param screen - screen code, for example "splash". Must be included
 * in navigation graph or will cause an error
 * @param startScreen - screen code to star
 */
fun RootController.present(screen: String, params: Any?, startScreen: String, startTabPosition: Int) {
    launch(screen = screen, params = params, startScreen = startScreen, startTabPosition = startTabPosition)
}
