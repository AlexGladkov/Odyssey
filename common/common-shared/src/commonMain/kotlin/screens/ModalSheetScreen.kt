package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.Odyssey
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration
import screens.views.ActionCell
import kotlin.random.Random

@Composable
fun ModalSheetScreen(onCloseClick: () -> Unit) {
    val rootController = LocalRootController.current
    val modalController = rootController.findModalController()

    Column(modifier = Modifier.background(Odyssey.color.primaryBackground)) {
        Text(modifier = Modifier.padding(16.dp), text = "Modal Sheet", fontSize = 24.sp, color = Odyssey.color.primaryText)
        ActionCell(text = "Close", icon = Icons.Filled.KeyboardArrowDown) {
            onCloseClick.invoke()
        }
        ActionCell(text = "Show one more Modal", icon = Icons.Filled.KeyboardArrowUp) {
            val modalSheetConfiguration = ModalSheetConfiguration(maxHeight = Random.nextFloat(), cornerRadius = 16)
            modalController.present(modalSheetConfiguration) {
                ModalSheetScreen {
                    modalController.popBackStack()
                }
            }
        }
        ActionCell(text = "Double-Modal show/close", icon = Icons.Filled.KeyboardArrowUp) {
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

        ActionCell(text = "Modal screen without closing animation", icon = Icons.Filled.KeyboardArrowUp) {
            val modalSheetConfiguration = ModalSheetConfiguration(maxHeight = Random.nextFloat(), cornerRadius = 16)
            modalController.present(modalSheetConfiguration) {
                ModalSheetScreen {
                    modalController.popBackStack(animate = false)
                }
            }
        }
    }
}