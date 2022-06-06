package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import screens.views.ActionCell
import theme.Odyssey
import utils.toSequence

@Composable
fun ActionScreen(count: Int?) {
    val rootController = LocalRootController.current

    Column(modifier = Modifier.fillMaxSize().background(Odyssey.color.primaryBackground)) {
        Spacer(modifier = Modifier.height(56.dp))
        CounterView(count)

        ActionCell(text = "Push Screen", icon = null) {
            rootController.push("push", (count ?: 0) + 1)
        }

        ActionCell("Present Flow", icon = null) {
            rootController.present("present", 0)
        }

        ActionCell("Back", icon = null) {
            rootController.popBackStack()
        }

        Box(
            modifier = Modifier.background(Odyssey.color.primaryBackground).fillMaxSize()
        ) {
//                item {
////                    val modalSheetConfiguration = ModalSheetConfiguration(maxHeight = 0.7f, cornerRadius = 16)
//                    ActionCell("Present Modal Screen", icon = null) {
////                        modalController.present(modalSheetConfiguration) {
////                            ModalSheetScreen {
////                                modalController.popBackStack()
////                            }
////                        }
//                    }
//                }
//
//                item {
//                    ActionCell("Show Alert Dialog", icon = null) {
////                        val alertConfiguration = AlertConfiguration(maxHeight = 0.7f, maxWidth = 0.8f, cornerRadius = 4)
////                        modalController.present(alertConfiguration) {
////                            AlertDialogScreen {
////                                modalController.popBackStack()
////                            }
////                        }
//                    }
//                }
//
//                item {
//                    ActionCell("Show Bottom Navigation", icon = null) {
////                        rootController.present("main")
//                    }
//                }
//
//                item {
//                    ActionCell("Show Top Navigation", icon = null
//                    ) {
////                        rootController.present("top")
//                    }
//                }
//
//                item {
//                    ActionCell("Show Drawer Navigation", icon = null) {
////                        rootController.present("drawer")
//                    }
//                }
//
//                item {
//                    ActionCell("Start New Chain", icon = null) {
////                        rootController.present(screen = "present", launchFlag = LaunchFlag.SingleNewTask)
//                    }
//                }
//
//                item {
//                    ActionCell(
//                        "Back",
//                        icon = if (count == 0 || count == null) Icons.Filled.Close else Icons.Filled.ArrowBack
//                    ) {
////                        rootController.popBackStack()
//                    }
//                }
//            }
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
            text = "Controller ${rootController.debugName?.capitalize()}, Level $level",
            color = Odyssey.color.primaryText
        )

        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = "Chain: ${count?.toSequence() ?: "[0]"}",
            color = Odyssey.color.primaryText
        )
    }
}