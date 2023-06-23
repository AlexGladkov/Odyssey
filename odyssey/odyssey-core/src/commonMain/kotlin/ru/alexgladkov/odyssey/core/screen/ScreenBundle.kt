package ru.alexgladkov.odyssey.core.screen

import androidx.compose.runtime.Immutable
import ru.alexgladkov.odyssey.core.CoreScreen

@Immutable
class ScreenBundle(
    override val key: String,
    override val realKey: String?,
    override val params: Any?
) : CoreScreen