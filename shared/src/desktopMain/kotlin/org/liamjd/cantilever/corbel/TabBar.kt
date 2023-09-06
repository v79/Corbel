package org.liamjd.cantilever.corbel

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.em
import org.liamjd.cantilever.corbel.ui.DarkColors
import org.liamjd.cantilever.corbel.models.Tabs

@Composable
fun TabBar(colorScheme: ColorScheme, onTabChange: (Int) -> Unit) {
    var tabIndex by remember { mutableStateOf(0) }
    Column(Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            Tabs.entries.forEachIndexed { index, title ->

                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index; onTabChange(tabIndex) }) {
                    Text(title.label, color = colorScheme.onSurface, fontSize = 1.em)
                }
            }
        }
    }
}


@Preview
@Composable
fun TabBarPreview() {
    val cl = DarkColors
    TabBar(cl) {}
}