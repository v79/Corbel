package org.liamjd.cantilever.corbel

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.liamjd.cantilever.corbel.ui.md_theme_dark_onSecondary
import org.liamjd.cantilever.corbel.ui.models.SubmitUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginDialog(onDismiss: () -> Unit, onSubmit: (SubmitUser) -> Unit) {
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
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(onClick = onDismiss) {
                            Text("Cancel")
                        }
                        ElevatedButton(onClick = { onSubmit(SubmitUser(usernmame,password)) }) {
                            Text("Login")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginDialogPreview() {
    LoginDialog(onDismiss = {}, onSubmit = {})
}