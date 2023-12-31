import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.anastr.shared.App


fun main() = application {
    Window(
        title = "Desktop App",
        onCloseRequest = ::exitApplication,
    ) {
        App()
    }
}
