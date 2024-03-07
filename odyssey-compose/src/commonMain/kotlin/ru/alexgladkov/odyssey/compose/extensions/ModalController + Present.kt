package ru.alexgladkov.odyssey.compose.extensions

import ru.alexgladkov.odyssey.compose.Render
import ru.alexgladkov.odyssey.compose.controllers.ModalController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.CustomModalConfiguration
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration

fun ModalController.present(
    modalSheetConfiguration: ModalSheetConfiguration,
    content: Render
) {
    presentNew(modalSheetConfiguration, content)
}

fun ModalController.present(
    alertConfiguration: AlertConfiguration,
    content: Render
) {
    presentNew(alertConfiguration, content)
}

fun ModalController.present(
    customModalConfiguration: CustomModalConfiguration,
    content: Render
) {
    presentNew(customModalConfiguration, content)
}
