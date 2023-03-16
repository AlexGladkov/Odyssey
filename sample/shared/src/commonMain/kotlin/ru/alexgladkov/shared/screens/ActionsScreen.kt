package ru.alexgladkov.shared.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.shared.NavigationTree
import ru.alexgladkov.shared.tests.TestTags
import ru.alexgladkov.shared.theme.Odyssey
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
internal fun ActionsScreen(count: Int?) {
    println("DEBUG: Draw action screen")
    val rootController = LocalRootController.current
    val modalController = rootController.findModalController()

    Column {
        CounterView(count)

        Box(
            modifier = Modifier.background(Odyssey.color.primaryBackground).fillMaxSize()
        ) {
            LazyColumn {
                item {
                    ActionCell(modifier = Modifier.testTag(TestTags.actionScreenPush), text = "Push Screen", icon = Icons.Filled.ArrowForward) {
                        println("Push Action Called")
                        rootController.push(NavigationTree.Push.name, (count ?: 0) + 1)
                    }
                }

                item {
                    ActionCell(modifier = Modifier.testTag(TestTags.actionScreenPresent), text = "Present Flow", icon = Icons.Filled.ArrowForward) {
                        println("Present Action Called")
                        rootController.present(NavigationTree.Present.name)
                    }
                }

                item {
                    val modalSheetConfiguration = ModalSheetConfiguration(maxHeight = 0.7f, cornerRadius = 16)
                    ActionCell(text = "Present Modal Screen", icon = Icons.Filled.KeyboardArrowUp) {
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
                        modifier = Modifier.testTag(TestTags.actionScreenBottomNavigation),
                        text = "Show Bottom Navigation",
                        icon = Icons.Filled.Create
                    ) {
                        rootController.present(NavigationTree.Main.name)
                    }
                }

                item {
                    ActionCell(
                        text = "Show Top Navigation",
                        icon = Icons.Filled.Create
                    ) {
                        rootController.present(NavigationTree.Top.name)
                    }
                }

                item {
                    ActionCell(
                        text = "Show Drawer Navigation",
                        icon = Icons.Filled.Create
                    ) {
                        rootController.present(NavigationTree.Drawer.name, params = "Custom Title from params")
                    }
                }

                item {
                    ActionCell(
                        text = "Start New Chain",
                        icon = Icons.Filled.Done
                    ) {
                        rootController.present(screen = NavigationTree.Present.name, launchFlag = LaunchFlag.SingleNewTask)
                    }
                }

                item {
                    ActionCell(
                        text = "Clear Previous Screen",
                        icon = Icons.Filled.Clear
                    ) {
                        rootController.push(screen = NavigationTree.Push.name, launchFlag = LaunchFlag.ClearPrevious, params = (count ?: 0) + 1)
                    }
                }

                item {
                    ActionCell(
                        modifier = Modifier.testTag(TestTags.actionScreenBack),
                        text = "Back",
                        icon = if (count == 0 || count == null) Icons.Filled.Close else Icons.Filled.ArrowBack
                    ) {
                        println("Back Action Called")
                        rootController.popBackStack()
                    }
                }
            }
        }
    }
}

@Composable
internal fun CounterView(count: Int?) {
    val rootController = LocalRootController.current
    val level = rootController.measureLevel()

    Column(
        Modifier.background(Odyssey.color.primaryBackground).fillMaxWidth().padding(16.dp),
    ) {
        Text(
            text = "Controller ${rootController.debugName}, Level $level",
            color = Odyssey.color.primaryText
        )

        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = "Chain: ${count?.toSequence() ?: "[0]"}",
            color = Odyssey.color.primaryText
        )
    }
}

@Composable
internal fun ActionCell(modifier: Modifier = Modifier, text: String, icon: ImageVector, onClick: () -> Unit) {
    Column(modifier = modifier
        .clickable { onClick.invoke() }
        .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = text, color = Odyssey.color.primaryText, fontSize = 18.sp)
            Spacer(modifier = Modifier.weight(1f))
            Icon(icon, contentDescription = "", tint = Odyssey.color.controlColor)
        }
    }
}

fun Int?.toSequence(): String {
    if (this == null) return "0"
    if (this == 0) return "0"

    val builder = StringBuilder()

    for (i in 0..this) {
        if (i == this) {
            builder.append(i)
        } else {
            builder.append(i).append(" -> ")
        }
    }

    return builder.toString()
}