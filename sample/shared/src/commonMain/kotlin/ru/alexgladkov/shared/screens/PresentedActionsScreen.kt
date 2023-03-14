package ru.alexgladkov.shared.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import ru.alexgladkov.shared.NavigationTree
import ru.alexgladkov.shared.tests.TestTags
import ru.alexgladkov.shared.theme.Odyssey
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.shared.screens.ActionCell
import ru.alexgladkov.shared.screens.AlertDialogScreen
import ru.alexgladkov.shared.screens.CounterView
import ru.alexgladkov.shared.screens.ModalSheetScreen

@Composable
internal fun PresentedActionsScreen(count: Int?) {
    val rootController = LocalRootController.current
    val modalController = rootController.findModalController()

    Column {
        CounterView(count)

        Box(
            modifier = Modifier.background(Odyssey.color.primaryBackground).fillMaxSize()
        ) {
            LazyColumn {
                item {
                    ActionCell(modifier = Modifier.testTag(TestTags.presentedActionScreenPush), text = "Push Screen", icon = Icons.Filled.ArrowForward) {
                        rootController.push(NavigationTree.PresentScreen.name, (count ?: 0) + 1)
                    }
                }

                item {
                    ActionCell(text = "Present Flow", icon = Icons.Filled.KeyboardArrowUp) {
                        rootController.findRootController().present(NavigationTree.Present.name)
                    }
                }

                item {
                    val modalSheetConfiguration = ModalSheetConfiguration(maxHeight = 0.7f, cornerRadius = 16)
                    ActionCell(text = "Present Modal Screen", icon = Icons.Filled.ThumbUp) {
                        modalController.present(modalSheetConfiguration) { key ->
                            ModalSheetScreen {
                                modalController.popBackStack(key)
                            }
                        }
                    }
                }

                item {
                    ActionCell(text = "Show Alert Dialog", icon = Icons.Filled.Warning) {
                        val alertConfiguration = AlertConfiguration(maxHeight = 0.7f, maxWidth = 0.8f, cornerRadius = 4)
                        modalController.present(alertConfiguration) { key ->
                            AlertDialogScreen {
                                modalController.popBackStack(key)
                            }
                        }
                    }
                }

                item {
                    ActionCell(
                        text = "Show Bottom Navigation",
                        icon = Icons.Filled.Create
                    ) {
                        rootController.findRootController().present(NavigationTree.Main.name)
                    }
                }

                item {
                    ActionCell(
                        text = "Start New Chain",
                        icon = Icons.Filled.Done
                    ) {
                        rootController.findRootController().present(screen = NavigationTree.Present.name, launchFlag = LaunchFlag.SingleNewTask)
                    }
                }

                item {
                    ActionCell(
                        text = "Start Single Instance",
                        icon = Icons.Filled.Create
                    ) {
                        rootController.findRootController().present(screen = NavigationTree.Present.name, launchFlag = LaunchFlag.SingleInstance)
                    }
                }

                item {
                    ActionCell(
                        modifier = Modifier.testTag(TestTags.presentedActionScreenBack),
                        text = "Back",
                        icon = if (count == 0 || count == null) Icons.Filled.KeyboardArrowDown
                        else Icons.Filled.ArrowBack
                    ) {
                        rootController.popBackStack()
                    }
                }
            }
        }
    }
}