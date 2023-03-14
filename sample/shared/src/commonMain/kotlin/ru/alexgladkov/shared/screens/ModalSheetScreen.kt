package ru.alexgladkov.shared.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.shared.theme.Odyssey
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration
import kotlin.random.Random
import ru.alexgladkov.shared.NavigationTree
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.compose.extensions.push


@Composable
internal fun ModalSheetScreen(onCloseClick: () -> Unit) {
    val rootController = LocalRootController.current
    val modalController = rootController.findModalController()

    Column(modifier = Modifier.background(Odyssey.color.primaryBackground)) {
        Text(modifier = Modifier.padding(16.dp), text = "Modal Sheet", fontSize = 24.sp, color = Odyssey.color.primaryText)
        ActionCell(text = "Close", icon = Icons.Filled.ArrowDropDown) {
            onCloseClick.invoke()
        }
        ActionCell(text = "Show one more Modal", icon = Icons.Filled.KeyboardArrowUp) {
            val modalSheetConfiguration = ModalSheetConfiguration(maxHeight = Random.nextFloat(), cornerRadius = 16)
            modalController.present(modalSheetConfiguration) { key ->
                ModalSheetScreen {
                    modalController.popBackStack(key)
                }
            }
        }
        ActionCell(text = "Double-Modal show/close", icon = Icons.Filled.ThumbUp) {
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
            modalController.present(modalSheetConfiguration.copy(maxHeight = height - 0.1f)) { key ->
                ModalSheetScreen {
                    modalController.popBackStack(key)
                    modalController.popBackStack(key)
                }
            }
        }

        ActionCell(text = "Modal screen without closing animation", icon = Icons.Filled.ThumbUp) {
            val modalSheetConfiguration = ModalSheetConfiguration(maxHeight = Random.nextFloat(), cornerRadius = 16)
            modalController.present(modalSheetConfiguration) { key ->
                ModalSheetScreen {
                    modalController.popBackStack(key, animate = false)
                }
            }
        }

        ActionCell(text = "Start New Chain", icon = Icons.Filled.Done) {
            rootController.present(
                screen = NavigationTree.Present.name,
                launchFlag = LaunchFlag.SingleNewTask
            )
        }

        ActionCell(text = "Clear Previous Screen", icon = Icons.Filled.Clear) {
            rootController.push(
                screen = NavigationTree.Push.name,
                launchFlag = LaunchFlag.ClearPrevious,
                params = 0
            )
        }

    }
}