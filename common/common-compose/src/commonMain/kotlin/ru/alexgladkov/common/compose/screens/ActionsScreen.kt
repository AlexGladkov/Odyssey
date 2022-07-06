package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.common.compose.theme.Odyssey
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
fun ActionsScreen(count: Int?) {
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
                        rootController.push("push", (count ?: 0) + 1)
                    }
                }

                item {
                    ActionCell("Present Flow", icon = Icons.Filled.ArrowUpward) {
                        rootController.present("present")
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
                        rootController.present("main")
                    }
                }

                item {
                    ActionCell(
                        "Show Top Navigation",
                        icon = Icons.Filled.SpaceDashboard
                    ) {
                        rootController.present("top")
                    }
                }

                item {
                    ActionCell(
                        "Show Drawer Navigation",
                        icon = Icons.Filled.DashboardCustomize
                    ) {
                        rootController.present("drawer")
                    }
                }

                item {
                    ActionCell(
                        "Start New Chain",
                        icon = Icons.Filled.OpenInNew
                    ) {
                        rootController.present(screen = "present", launchFlag = LaunchFlag.SingleNewTask)
                    }
                }

                item {
                    ActionCell(
                        "Start Hilt demo",
                        icon = Icons.Filled.ArrowForward
                    ) {
                        rootController.push(screen = "hilt")
                    }
                }

                item {
                    ActionCell(
                        "Back",
                        icon = if (count == 0 || count == null) Icons.Filled.Close else Icons.Filled.ArrowBack
                    ) {
                        rootController.popBackStack()
                    }
                }
            }
        }
    }
}

@Composable
fun CounterView(count: Int?) {
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
fun ActionCell(text: String, icon: ImageVector, onClick: () -> Unit) {
    Column(modifier = Modifier
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