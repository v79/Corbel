import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.mayakapps.compose.windowstyler.WindowBackdrop
import com.mayakapps.compose.windowstyler.WindowFrameStyle
import com.mayakapps.compose.windowstyler.WindowStyle
import org.liamjd.cantilever.corbel.DesktopApp

fun main() = application {
    val dark = isSystemInDarkTheme()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Corbel Editor",
        state = rememberWindowState(width = 1024.dp, height = 768.dp)
    ) {
        WindowStyle(
            isDarkTheme = dark,
            backdropType = WindowBackdrop.Mica,
            frameStyle = WindowFrameStyle()
        )
        DesktopApp(isDark = true)
    }
}

@Preview
@Composable
fun Wibble() {
    Text(text = "Wibble", color = Color.Blue)
}