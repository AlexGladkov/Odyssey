package ru.alexgladkov.odyssey.core

import ru.alexgladkov.odyssey.core.animations.AnimationType

data class NavigationParams(val launchFlags: List<LaunchFlag>, val animationType: AnimationType)

class NavigationParamsBuilder {

    private val launchFlags: MutableList<LaunchFlag> = mutableListOf()
    var animationType: AnimationType = AnimationType.None

    fun launchFlag(launchFlag: LaunchFlag) {
        launchFlags.add(launchFlag)
    }

    fun build(): NavigationParams = NavigationParams(launchFlags, animationType)
}
