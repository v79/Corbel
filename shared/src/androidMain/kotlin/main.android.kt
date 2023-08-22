import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

actual fun getPlatformName(): String = "Android"

@Composable fun MainView() = CommonAndroidApp()


@Preview
@Composable
fun Wibble() {
    Text(text = "Wibble")
}