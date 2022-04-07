package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.common.compose.theme.Odyssey
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration
import kotlin.random.Random


@Composable
fun ModalSheetScreen(onCloseClick: () -> Unit) {
    val rootController = LocalRootController.current
    val modalController = rootController.findModalController()

    Column(modifier = Modifier.background(Odyssey.color.primaryBackground)) {
        Text(modifier = Modifier.padding(16.dp), text = "Modal Sheet", fontSize = 24.sp, color = Odyssey.color.primaryText)
        ActionCell(text = "Close", icon = Icons.Filled.ArrowDownward) {
            onCloseClick.invoke()
        }
        ActionCell(text = "Show one more Modal", icon = Icons.Filled.ArrowCircleUp) {
            val modalSheetConfiguration = ModalSheetConfiguration(maxHeight = Random.nextFloat(), cornerRadius = 16)
            modalController.present(modalSheetConfiguration) {
                ModalSheetScreen {
                    modalController.popBackStack()
                }
            }
        }
        ActionCell(text = "Double-Modal show/close", icon = Icons.Filled.ArrowCircleUp) {
            val height = Random.nextFloat().coerceAtLeast(0.4f)
            val modalSheetConfiguration = ModalSheetConfiguration(
                maxHeight = height,
                cornerRadius = 16
            )
            modalController.present(modalSheetConfiguration) {
                ModalSheetScreen {
// close by next modal
                }
            }
            modalController.present(modalSheetConfiguration.copy(maxHeight = height - 0.1f)) {
                ModalSheetScreen {
                    modalController.popBackStack()
                    modalController.popBackStack()
                }
            }
        }
    }
}