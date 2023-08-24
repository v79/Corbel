package org.liamjd.cantilever.corbel

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.liamjd.cantilever.corbel.ui.AppTheme

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
                    LoginDialog()
                }
            }
        }
    }
}