package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration
import screens.views.ActionCell
import theme.Odyssey
import utils.toSequence

@Composable
fun ActionScreen(count: Int?) {
    val rootController = LocalRootController.current
    val modalController = rootController.findModalController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Odyssey.color.primaryBackground)
    ) {
        Spacer(modifier = Modifier.height(56.dp))
        CounterView(count)

        ActionCell(text = "Push Screen", icon = Icons.Filled.ArrowForward) {
            rootController.push("push", (count ?: 0) + 1)
        }

        ActionCell("Present Flow", icon = Icons.Filled.KeyboardArrowUp) {
            rootController.present("present", 0)
        }

        ActionCell("Back", icon = Icons.Filled.ArrowBack) {
            rootController.popBackStack()
        }

        val modalSheetConfiguration = ModalSheetConfiguration(maxHeight = 0.7f, cornerRadius = 16)
        ActionCell("Present Modal Screen", icon = Icons.Filled.KeyboardArrowUp) {
            modalController.present(modalSheetConfiguration) {
                ModalSheetScreen {
                    modalController.popBackStack()
                }
            }
        }

        ActionCell("Show Alert Dialog", icon = Icons.Filled.Settings) {
            val alertConfiguration = AlertConfiguration(maxHeight = 0.7f, maxWidth = 0.8f, cornerRadius = 4)
            modalController.present(alertConfiguration) {
                AlertDialogScreen {
                    modalController.popBackStack()
                }
            }
        }

        ActionCell("Show Bottom Navigation", icon = Icons.Filled.Home) {
            rootController.present("main")
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

//                }
//
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