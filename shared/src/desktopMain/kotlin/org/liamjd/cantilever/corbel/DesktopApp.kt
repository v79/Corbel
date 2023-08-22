package org.liamjd.cantilever.corbel

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import org.liamjd.cantilever.corbel.ui.DarkColors
import org.liamjd.cantilever.corbel.ui.LightColors
import org.liamjd.cantilever.corbel.ui.models.Tabs


@Composable
fun DesktopApp(isDark: Boolean = true) {
    val colorScheme = if (isDark) DarkColors else LightColors
    var currentPage = mutableStateOf(Tabs.POSTS)

    MaterialTheme(colorScheme = colorScheme) {
        Surface(Modifier.fillMaxSize(), color = colorScheme.surface) {
            Column(Modifier.padding(4.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    FilledTonalButton(
                        onClick = { },
                        colors = ButtonDefaults.filledTonalButtonColors(containerColor = colorScheme.tertiaryContainer)
                    ) {
                        Text("Login")
                    }
                    FilledTonalButton(onClick = { }) {
                        Text("Logout")
                    }
                }
                Row {
                    TabBar(colorScheme = colorScheme, onTabChange = {
                        when (it) {
                            0 -> currentPage.value = Tabs.POSTS
                            1 -> currentPage.value = Tabs.PAGES
                            2 -> currentPage.value = Tabs.TEMPLATES
                            else -> {
                                currentPage.value = Tabs.POSTS
                            }
                        }
                    })
                }
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Column {
                        Text(
                            currentPage.value.name,
                            color = colorScheme.onSurface,
                            fontSize = TextUnit(
                                8f,
                                TextUnitType.Em
                            )
                        )

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

