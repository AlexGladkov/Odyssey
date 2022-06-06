package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import screens.views.ActionCell
import theme.Odyssey

@Composable
fun PresentedActionsScreen(count: Int?) {
    val rootController = LocalRootController.current

    Column(modifier = Modifier.fillMaxSize().background(Odyssey.color.primaryBackground)) {
        Spacer(modifier = Modifier.height(56.dp))
        CounterView(count)

        ActionCell(text = "Push Screen", icon = null) {
            rootController.push("present_screen", (count ?: 0) + 1)
        }

        ActionCell("Present Flow", icon = null) {
            rootController.findRootController().present("present", 0)
        }

        ActionCell("Back", icon = null) {
            rootController.popBackStack()
        }
    }
}