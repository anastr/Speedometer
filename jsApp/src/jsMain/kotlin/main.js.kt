import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import com.github.anastr.shared.MainView
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        Window("Speedometer") {
            Column(modifier = Modifier.fillMaxSize()) {
                MainView()
            }
        }
    }
}