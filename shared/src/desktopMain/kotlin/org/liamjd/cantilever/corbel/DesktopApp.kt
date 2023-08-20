package org.liamjd.cantilever.corbel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.liamjd.cantilever.corbel.ui.DarkColors
import org.liamjd.cantilever.corbel.ui.LightColors

@Composable
fun DesktopApp(isDark: Boolean = true) {
    val colorScheme = if (isDark) DarkColors else LightColors
    MaterialTheme(colorScheme = colorScheme) {
        Surface(Modifier.fillMaxSize(), color = colorScheme.surface) {
            Column {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Column {
                        Text("Corbel Editor", color = colorScheme.onSurface)

                    }
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Column {
                        ElevatedButton(
                            onClick = { },
                            colors = ButtonDefaults.elevatedButtonColors(containerColor = DarkColors.onSurfaceVariant)
                        ) {
                            Text("Colour is awful?")
                        }
                    }
                }
            }
        }
    }
}