package org.liamjd.cantilever.corbel

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import org.liamjd.cantilever.corbel.ui.AppTheme
import org.liamjd.cantilever.corbel.ui.DarkColors
import org.liamjd.cantilever.corbel.ui.LightColors
import org.liamjd.cantilever.corbel.ui.md_theme_dark_onSurfaceVariant
import org.liamjd.cantilever.corbel.ui.models.Tabs


@Composable
fun DesktopApp(isDark: Boolean = true) {
    var appState by remember { mutableStateOf(AppState()) }

    var showLoginDialog by remember { mutableStateOf(false) }
    val colorScheme = if (isDark) DarkColors else LightColors
    val currentTab = mutableStateOf(Tabs.POSTS)

    AppTheme(useDarkTheme = isDark) {
        Surface(Modifier.fillMaxSize(), color = colorScheme.surface) {

            if (showLoginDialog) {
                Column {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        LoginDialog()
                    }
                }
            } else {
                Column(Modifier.padding(4.dp)) {
                    if (appState.mode == Mode.UNAUTHENTICATED) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            FilledTonalButton(
                                onClick = { showLoginDialog = true },
                                colors = ButtonDefaults.filledTonalButtonColors()
                            ) {
                                Text("Login")
                            }

                        }
                    } else {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            FilledTonalButton(onClick = {
                                appState = appState.copy(mode = Mode.UNAUTHENTICATED)
                            }) {
                                Text("Logout")
                            }
                        }
                        Row {
                            TabBar(colorScheme = colorScheme, onTabChange = {
                                when (it) {
                                    0 -> currentTab.value = Tabs.POSTS
                                    1 -> currentTab.value = Tabs.PAGES
                                    2 -> currentTab.value = Tabs.TEMPLATES
                                    else -> {
                                        currentTab.value = Tabs.POSTS
                                    }
                                }
                            })
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Column {
                                Text(
                                    currentTab.value.name,
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
                                    colors = ButtonDefaults.elevatedButtonColors()
                                ) {
                                    TextField(value = "Theming is weird", onValueChange = {})
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun LoginDialog() {
    var usernmame by remember { mutableStateOf("") }
    Box(Modifier.border(4.dp, color = LightColors.background, RoundedCornerShape(2.dp)))
    androidx.compose.material3.OutlinedTextField(
        value = usernmame,
        onValueChange = { usernmame = it },
        label = { Text("Username") },
        placeholder = { Text("example@gmail.com") },
    )
}
