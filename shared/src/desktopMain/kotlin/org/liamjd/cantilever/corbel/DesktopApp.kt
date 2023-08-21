package org.liamjd.cantilever.corbel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import org.liamjd.cantilever.corbel.ui.DarkColors
import org.liamjd.cantilever.corbel.ui.LightColors

enum class Tabs {
    POSTS,
    PAGES,
    TEMPLATES
}

@Composable
fun DesktopApp(isDark: Boolean = true) {
    val colorScheme = if (isDark) DarkColors else LightColors
    var currentPage = mutableStateOf(Tabs.POSTS)
    var tabIndex = mutableStateOf(0)

    MaterialTheme(colorScheme = colorScheme) {
        Surface(Modifier.fillMaxSize(), color = colorScheme.surface) {
            Column(Modifier.padding(4.dp)) {
                MenuBar(colorScheme = colorScheme, onTabChange = {
                    when (it) {
                        0 -> currentPage.value = Tabs.POSTS
                        1 -> currentPage.value = Tabs.PAGES
                        2 -> currentPage.value = Tabs.TEMPLATES
                        else -> {
                            currentPage.value = Tabs.POSTS
                        }
                    }
                })
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

@Composable
fun MenuBar(colorScheme: ColorScheme, onTabChange: (Int) -> Unit) {
    var tabIndex by remember { mutableStateOf(0) }
    Column(Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            Tabs.entries.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index; onTabChange(tabIndex) }) {
                    Text(title.name, color = colorScheme.onSurface)
                }
            }
        }

    }
}