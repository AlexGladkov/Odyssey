package ru.alexgladkov.odyssey.core

enum class LaunchFlag {
    SingleTop, // Always save oldest copy in stack
    NewSingleTask, // Always save newest copy in stack
    ClearHistory // Clear previous stack
}