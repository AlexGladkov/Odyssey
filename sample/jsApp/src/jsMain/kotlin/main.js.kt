import androidx.compose.ui.window.Window
import org.jetbrains.skiko.wasm.onWasmReady
import ru.alexgladkov.shared.MainView

fun main() {
    onWasmReady {
        Window("Odyssey Demo") {
            MainView()
        }
    }
}