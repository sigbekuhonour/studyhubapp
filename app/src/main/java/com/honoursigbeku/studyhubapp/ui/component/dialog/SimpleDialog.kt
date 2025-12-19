package com.honoursigbeku.studyhubapp.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Dialog

@Composable
fun SimpleDialog(
    dialogHeader: String,
    labelText: String,
    isSingleLine: Boolean,
    confirmButtonText: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var newValueToBeAdded by rememberSaveable { mutableStateOf("") }
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(shape = MaterialTheme.shapes.extraLarge) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = dialogHeader, style = MaterialTheme.typography.titleLarge)
                TextField(
                    value = newValueToBeAdded,
                    onValueChange = { newValueToBeAdded = it },
                    label = { Text(text = labelText) },
                    singleLine = isSingleLine
                )
                Button(
                    onClick = {
                        if (newValueToBeAdded.isNotBlank()) {
                            onConfirm(newValueToBeAdded)
                            onDismiss()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outline)
                ) {
                    Text(text = confirmButtonText, color = MaterialTheme.colorScheme.scrim)
                }
                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outline)
                ) {
                    Text(text = "Cancel", color = MaterialTheme.colorScheme.scrim)
                }
            }
        }
    }
}
