import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


actual fun getPlatformName(): String = "Android"

@Composable fun MainView() = CommonAndroidApp()



@Composable
fun Wibble() {
    Text(text = "Wibble")
}