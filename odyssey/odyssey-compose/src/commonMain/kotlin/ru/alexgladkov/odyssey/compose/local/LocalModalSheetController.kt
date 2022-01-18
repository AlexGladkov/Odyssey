package ru.alexgladkov.odyssey.compose.local

import androidx.compose.runtime.compositionLocalOf
import ru.alexgladkov.odyssey.compose.controllers.ModalSheetController

val LocalModalSheetController = compositionLocalOf<ModalSheetController> {
    error("No root controller provider")
}