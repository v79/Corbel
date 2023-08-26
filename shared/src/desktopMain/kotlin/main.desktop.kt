import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import org.liamjd.cantilever.corbel.DesktopApp

actual fun getPlatformName(): String = "Desktop"

@Composable
fun MainView() = DesktopApp(window = ComposeWindow(null))

@Preview
@Composable
fun AppPreview() {
    DesktopApp(window = ComposeWindow(null))
}