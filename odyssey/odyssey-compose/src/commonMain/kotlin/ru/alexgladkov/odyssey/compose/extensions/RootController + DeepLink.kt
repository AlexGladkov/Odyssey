package ru.alexgladkov.odyssey.compose.extensions

import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.animations.defaultPresentationAnimation
import ru.alexgladkov.odyssey.core.animations.defaultPushAnimation

/**
 * Helper function to start deep link process
 * If app opens from deeplink it will route to link contains inside
 * or route to destinated screen if don't have
 * @param screen - screen code, for example "splash". Must be included
 * in navigation graph or will cause an error
 * @param params - any bunch of params you need for the screen
 * @param animationType - animation of presentation
 * @param launchFlag - flag if you want to change default behavior @see LaunchFlag
 */
fun RootController.deepLink(screen: String, params: Any? = null, animationType: AnimationType, launchFlag: LaunchFlag? = null) {
    launch(
        screen = screen,
        params = params,
        deepLink = true,
        animationType = animationType,
        launchFlag = launchFlag
    )
}

/**
 * Helper function to start deep link process
 * If app opens from deeplink it will route to link contains inside
 * or route to destinated screen if don't have
 * @param screen - screen code, for example "splash". Must be included
 * in navigation graph or will cause an error
 * @param params - any bunch of params you need for the screen
 * @param animationType - animation of presentation
 */
fun RootController.deepLink(screen: String, params: Any? = null, animationType: AnimationType) {
    launch(
        screen = screen,
        params = params,
        deepLink = true,
        animationType = animationType,
        launchFlag = null
    )
}

/**
 * Helper function to start deep link process
 * If app opens from deeplink it will route to link contains inside
 * or route to destinated screen if don't have
 * @param screen - screen code, for example "splash". Must be included
 * in navigation graph or will cause an error
 * @param params - any bunch of params you need for the screen
 */
fun RootController.presentDeepLink(screen: String, params: Any? = null) {
    launch(
        screen = screen,
        params = params,
        deepLink = true,
        animationType = defaultPresentationAnimation(),
        launchFlag = null
    )
}

/**
 * Helper function to start deep link process
 * If app opens from deeplink it will route to link contains inside
 * or route to destinated screen if don't have
 * @param screen - screen code, for example "splash". Must be included
 * in navigation graph or will cause an error
 * @param params - any bunch of params you need for the screen
 */
fun RootController.pushDeepLink(screen: String, params: Any? = null) {
    launch(
        screen = screen,
        params = params,
        deepLink = true,
        animationType = defaultPushAnimation(),
        launchFlag = null
    )
}