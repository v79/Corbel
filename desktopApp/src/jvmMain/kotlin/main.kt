import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val windowState = rememberWindowState(width = 1024.dp, height = 768.dp)
    val windowTitle = remember { mutableStateOf("Corbel Editor") }

    val window = Window(
        onCloseRequest = ::exitApplication,
        title = windowTitle.value,
        state = windowState
    ) {
        WindowStyle(
            isDarkTheme = dark,
            backdropType = WindowBackdrop.Mica,
            frameStyle = WindowFrameStyle()
        )
        DesktopApp(isDark = true, window)
    }
}
