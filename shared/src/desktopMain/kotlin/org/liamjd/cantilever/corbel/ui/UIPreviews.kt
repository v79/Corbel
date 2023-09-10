package org.liamjd.cantilever.corbel.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import org.liamjd.cantilever.corbel.LoginDialog
import org.liamjd.cantilever.corbel.models.Tabs

@Preview
@Composable
fun TestBox() {
    AppTheme(useDarkTheme = true) {
        Surface(Modifier.fillMaxSize()) {
            Column(
                Modifier.border(width = 2.dp, color = Color.Blue).fillMaxHeight(0.5f)
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(Modifier.fillMaxWidth(0.3f)) {
                    LoginDialog(onDismiss = {}, onSubmit = {})
                }
            }
        }
    }
}

@Preview
@Composable
fun BasicLayout() {
    AppTheme {
        Surface(Modifier.fillMaxSize().border(4.dp, Color.Red)) {
            Column(Modifier.padding(4.dp), verticalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Row(Modifier.fillMaxWidth().border(2.dp, Color.Yellow)) {
                        Text("Top row")
                    }
                    Row(Modifier.fillMaxWidth().border(2.dp, Color.Green)) {
                        Text("Center area")
                    }
                }
                Row(Modifier.weight(1f, false).fillMaxWidth().border(2.dp, Color.Magenta)) {
                    Text("Status bar")
                }
            }
        }
    }
}

@Preview
@Composable
fun PrimaryNavigationLayout() {
    var tabIndex by remember { mutableStateOf(0) }
    Row(Modifier.fillMaxWidth().border(2.dp, Color.Red), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Bottom) {
        Column(Modifier.weight(0.5f).border(2.dp, Color.Green)) {
            TabRow(selectedTabIndex = tabIndex) {
                Tabs.entries.forEachIndexed { index, title ->
                    val isSelected = tabIndex == index
                    Tab(
                        selected = isSelected,
                        onClick = { tabIndex = index }) {
                        androidx.compose.material3.Text(
                            modifier = Modifier.padding(2.dp),
                            text = title.label,
                            color = colorScheme.onSurface,
                            fontSize = 1.6.em
                        )
                    }
                }
            }
        }
        Column(Modifier.weight(0.5f).border(2.dp, Color.Yellow), horizontalAlignment = Alignment.End) {
            FilledTonalButton(onClick = {
//            viewModel.logout()
            }) {
                Text("Logout")
            }
        }
    }
}