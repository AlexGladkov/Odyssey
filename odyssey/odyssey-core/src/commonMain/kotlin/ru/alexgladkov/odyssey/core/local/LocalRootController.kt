package ru.alexgladkov.odyssey.core.local

import androidx.compose.runtime.compositionLocalOf
import ru.alexgladkov.odyssey.core.RootController

val LocalRootController = compositionLocalOf<RootController> {
    error("No root controller provider")
}