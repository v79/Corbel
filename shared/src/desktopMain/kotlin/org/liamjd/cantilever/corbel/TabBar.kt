package org.liamjd.cantilever.corbel

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import org.liamjd.cantilever.corbel.models.Tabs
import org.liamjd.cantilever.corbel.ui.DarkColors

@Composable
fun PrimaryNavigation(colorScheme: ColorScheme, onTabChange: (Int) -> Unit, onLogout: () -> Unit = {}) {
    var tabIndex by remember { mutableStateOf(0) }
    Row(
        Modifier.fillMaxWidth().drawBehind {
            val strokeWidth = density
            val y = size.height - strokeWidth / 2
            drawLine(
                colorScheme.onSecondaryContainer,
                Offset(2f, y),
                Offset(size.width, y),
                strokeWidth
            )
        },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(Modifier.weight(0.8f)) {
            TabRow(selectedTabIndex = tabIndex ) {
                Tabs.entries.forEachIndexed { index, title ->
                    val isSelected = tabIndex == index
                    Tab(
                        selected = isSelected,
                        onClick = { tabIndex = index; onTabChange(tabIndex) }) {
                        Text(
                            modifier = Modifier.padding(2.dp),
                            text = title.label,
                            color = colorScheme.onSurface,
                            fontSize = 1.4.em
                        )
                    }
                }
            }
        }
        Column(Modifier.weight(0.2f), horizontalAlignment = Alignment.End) {
            FilledTonalButton(onClick = onLogout) {
                Text("Logout")
            }
        }
    }
}


@Preview
@Composable
fun TabBarPreview() {
    val cl = DarkColors
    PrimaryNavigation(cl, onTabChange = {}, onLogout = {})
}