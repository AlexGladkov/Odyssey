package ru.alexgladkov.odyssey.compose.local

import androidx.compose.runtime.compositionLocalOf
import ru.alexgladkov.odyssey.core.RootController

val LocalRootController = compositionLocalOf<RootController> {
    error("No root controller provider")
}