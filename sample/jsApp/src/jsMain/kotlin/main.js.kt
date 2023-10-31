import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.skiko.wasm.onWasmReady
import ru.alexgladkov.common.compose.navigation.navigationGraph
import ru.alexgladkov.common.compose.theme.Odyssey
import ru.alexgladkov.common.compose.theme.OdysseyTheme
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow("Odyssey Demo") {
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
    }
}