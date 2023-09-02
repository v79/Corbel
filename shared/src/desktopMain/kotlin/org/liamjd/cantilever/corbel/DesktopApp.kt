package org.liamjd.cantilever.corbel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.liamjd.cantilever.corbel.models.Tabs
import org.liamjd.cantilever.corbel.ui.AppTheme
import org.liamjd.cantilever.corbel.ui.DarkColors
import org.liamjd.cantilever.corbel.ui.LightColors
import org.liamjd.cantilever.corbel.ui.components.StatusBar
import org.liamjd.cantilever.corbel.viewModels.CorbelViewModel
import org.liamjd.cantilever.corbel.viewModels.Mode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesktopApp(isDark: Boolean = true, window: ComposeWindow) {
    val viewModel = remember { CorbelViewModel() }
    val mode = remember { viewModel.mode }
    val windowTitle = remember { viewModel.windowTitle }
    val crScope = rememberCoroutineScope()

    val colorScheme = if (isDark) DarkColors else LightColors
    val currentTab = mutableStateOf(Tabs.POSTS)
    window.title = windowTitle.value

    AppTheme(useDarkTheme = isDark) {
        Surface(Modifier.fillMaxSize(), color = colorScheme.surface) {
            Column(Modifier.padding(4.dp), verticalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Row(Modifier.fillMaxWidth()) {

                        // Determine what we need to display based on the mode
                        when (mode.value) {
                            Mode.UNAUTHENTICATED -> {
                                ElevatedButton(onClick = { crScope.launch { viewModel.login() } }) {
                                    Text("Login")
                                }
                            }

                            Mode.BUSY_AWAITING_AUTH -> {
                                Text("Awaiting authentication")
                            }

                            Mode.BUSY -> {
                                Text("Busy")
                            }

                            Mode.VIEWING -> {
                                Column(Modifier.fillMaxWidth()) {
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        FilledTonalButton(onClick = {
                                            viewModel.logout()
                                        }) {
                                            Text("Logout")
                                        }
                                    }
                                    Row(Modifier.fillMaxWidth()) {
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
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
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
                                }
                            }

                            Mode.NEW_ITEM -> {
                                Text("New item")
                            }

                            Mode.EDITING -> {
                                Text("Editing")
                            }
                        }
                    }
                }
                Row(Modifier.weight(1f, false).fillMaxWidth()) {
//                    StatusBar(left = { Text("Left")}, center = { Text("Center")}, right = { Text("Right")})
                    val count = mutableStateOf(1)
                    StatusBar(right = { Text(text="Right is good, but not in politics ${count.value}", modifier = Modifier.clickable { count.value++ }) })
                }
            }
        }
    }
}

