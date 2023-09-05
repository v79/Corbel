package org.liamjd.cantilever.corbel.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.liamjd.cantilever.corbel.ui.DarkColors

/**
 * Draws a horizontal status bar divided into left, center and right components. Each is optional, though if all are null then nothing will be displayed at all.
 */
@Composable
fun StatusBar(
    modifier: Modifier = Modifier,
    left: @Composable (() -> Unit)? = null,
    center: @Composable (() -> Unit)? = null,
    right: @Composable (() -> Unit)? = null
) {
    Row(
        Modifier.fillMaxWidth().padding(2.dp).border(1.dp, DarkColors.primaryContainer)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(Modifier.padding(start = 2.dp)) { left?.invoke() }
        Box { center?.invoke() }
        Box(Modifier.padding(end = 2.dp)) { right?.invoke() }
    }
}

@Preview
@Composable
private fun StatusBarPreview() {
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
private fun EmptyStatusBar() {
    StatusBar()
}

@Preview
@Composable
private fun JustRightStatusBar() {
    StatusBar(right = { Text(text = "Right is good, but not in politics") })
}

@Preview
@Composable
fun WithAdditionalModifier() {
    StatusBar(
        modifier = Modifier.background(
            color = Color.Magenta,
            shape = RoundedCornerShape(4.dp)
        ),
        center = {
            Text(
                text = "This status bar has been modified!",
                modifier = Modifier.background(color = Color.Yellow)
            )
        })
}