package ru.alexgladkov.odyssey.core


/**
 * Add specific behavior to navigation
 * @param SingleNewTask - clears backstack and start new screen without history
 * @param SingleInstance - clears all previous instances of this screen
 * @param ClearPrevious - remove previous screen from backstack
 */
enum class LaunchFlag {
    SingleNewTask, SingleInstance, ClearPrevious
}