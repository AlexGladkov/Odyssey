import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import ru.alexgladkov.common.compose.navigation.navigationGraph
import ru.alexgladkov.common.compose.theme.Odyssey
import ru.alexgladkov.common.compose.theme.OdysseyTheme
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent

fun MainViewController(): UIViewController =
    ComposeUIViewController {
        OdysseyTheme {
            val configuration = OdysseyConfiguration(
                backgroundColor = Odyssey.color.primaryBackground
            )

            setNavigationContent(configuration, onApplicationFinish = {
                println("Exit")
            }) {
                navigationGraph()
            }
        }
    }