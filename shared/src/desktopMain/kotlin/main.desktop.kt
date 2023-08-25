import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import org.liamjd.cantilever.corbel.DesktopApp

actual fun getPlatformName(): String = "Desktop"

@Composable
fun MainView() = DesktopApp()

@Preview
@Composable
fun AppPreview() {
    DesktopApp()
}