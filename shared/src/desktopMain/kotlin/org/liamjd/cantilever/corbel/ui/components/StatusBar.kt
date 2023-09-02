package org.liamjd.cantilever.corbel.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun StatusBar(
    left: @Composable (() -> Unit)? = null,
    center: @Composable (() -> Unit)? = null,
    right: @Composable (() -> Unit)? = null
) {
    Row(
        Modifier.fillMaxWidth().padding(2.dp).border(1.dp, Color.Gray),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(Modifier.padding(start = 2.dp)) { left?.invoke() }
        Box { center?.invoke() }
        Box(Modifier.padding(end = 2.dp)) { right?.invoke() }
    }
}


@Preview
@Composable
internal fun StatusBarPreview() {
    StatusBar(
        left = {
            Text(buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Pages: ")
                }
                append("23")
            })
        },
        center = { Text("Center text here") },
        right = { Text("On the right") })
}

@Preview
@Composable
internal fun EmptyStatusBar() {
    StatusBar()
}

@Preview
@Composable
internal fun JustRightStatusBar() {
    StatusBar(right = { Text("Right is good, but not in politics") })
}