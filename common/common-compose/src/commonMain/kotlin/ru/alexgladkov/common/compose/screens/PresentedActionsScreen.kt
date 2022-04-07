package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.alexgladkov.common.compose.theme.Odyssey
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
fun PresentedActionsScreen(count: Int?) {
    val rootController = LocalRootController.current
    val modalController = rootController.findModalController()

    Column {
        CounterView(count)

        Box(
            modifier = Modifier.background(Odyssey.color.primaryBackground).fillMaxSize()
        ) {
            LazyColumn {
                item {
                    ActionCell(text = "Push Screen", icon = Icons.Filled.ArrowForward) {
                        rootController.push("present_screen", (count ?: 0) + 1)
                    }
                }

                item {
                    ActionCell("Present Flow", icon = Icons.Filled.ArrowUpward) {
                        rootController.findRootController().present("present")
                    }
                }

                item {
                    val modalSheetConfiguration = ModalSheetConfiguration(maxHeight = 0.7f, cornerRadius = 16)
                    ActionCell("Present Modal Screen", icon = Icons.Filled.ArrowCircleUp) {
                        modalController.present(modalSheetConfiguration) {
                            ModalSheetScreen {
                                modalController.popBackStack()
                            }
                        }
                    }
                }

                item {
                    ActionCell("Show Alert Dialog", icon = Icons.Filled.AddAlert) {
                        val alertConfiguration = AlertConfiguration(maxHeight = 0.7f, maxWidth = 0.8f, cornerRadius = 4)
                        modalController.present(alertConfiguration) {
                            AlertDialogScreen {
                                modalController.popBackStack()
                            }
                        }
                    }
                }

                item {
                    ActionCell(
                        "Show Bottom Navigation",
                        icon = Icons.Filled.Dashboard
                    ) {
                        rootController.findRootController().present("main")
                    }
                }

                item {
                    ActionCell(
                        "Start New Chain",
                        icon = Icons.Filled.OpenInNew
                    ) {
                        rootController.findRootController().present(screen = "present", launchFlag = LaunchFlag.SingleNewTask)
                    }
                }

                item {
                    ActionCell(
                        "Back",
                        icon = if (count == 0 || count == null) Icons.Filled.ArrowDownward
                        else Icons.Filled.ArrowBack
                    ) {
                        rootController.popBackStack()
                    }
                }
            }
        }
    }
}