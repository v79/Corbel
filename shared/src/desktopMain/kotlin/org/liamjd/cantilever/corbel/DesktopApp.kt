package org.liamjd.cantilever.corbel

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import org.liamjd.cantilever.corbel.ui.AppTheme
import org.liamjd.cantilever.corbel.ui.DarkColors
import org.liamjd.cantilever.corbel.ui.LightColors
import org.liamjd.cantilever.corbel.ui.md_theme_dark_onSecondary
import org.liamjd.cantilever.corbel.ui.models.Tabs


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesktopApp(isDark: Boolean = true) {
    var appState by remember { mutableStateOf(AppState()) }

    var showLoginDialog by remember { mutableStateOf(false) }
    val colorScheme = if (isDark) DarkColors else LightColors
    val currentTab = mutableStateOf(Tabs.POSTS)

    AppTheme(useDarkTheme = isDark) {
        Surface(Modifier.fillMaxSize(), color = colorScheme.surface) {



            if (showLoginDialog) {
                LoginDialog()
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
                                    OutlinedTextField(
                                        value = "Theming is weird",
                                        onValueChange = {})
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
    var password by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxHeight(0.5f).padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            Modifier.fillMaxWidth(0.5f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row {
                Column(
                    Modifier.border(
                        width = 2.dp,
                        color = md_theme_dark_onSecondary,
                        shape = RoundedCornerShape(4.dp)
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Login to Corbel")
                    OutlinedTextField(
                        value = usernmame,
                        singleLine = true,
                        onValueChange = { usernmame = it },
                        label = { Text("Username") },
                        placeholder = { Text("example@gmail.com") },
                    )
                    OutlinedTextField(
                        value = password,
                        singleLine = true,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        placeholder = { Text("****") },
                    )
                    Row(
                        Modifier.fillMaxWidth(0.8f).padding(end = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        ElevatedButton(onClick = { Unit }) {
                            Text("Login")
                        }
                    }
                }
            }
        }
    }
}


