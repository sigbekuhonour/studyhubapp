package com.example.studyhubapp.component.notefolder

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.studyhubapp.R
import com.example.studyhubapp.component.note.Note


data class NoteFolder(
    val id: Int,
    val icon: Int = R.drawable.folder_icon,
    val name: String,
    val listOfNotes: SnapshotStateList<Note> = mutableStateListOf()
)

@Composable
fun AddFolderDialog(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var folderName by rememberSaveable { mutableStateOf("") }
    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = MaterialTheme.shapes.extraLarge) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Create New Folder", style = MaterialTheme.typography.titleLarge)
                TextField(
                    value = folderName,
                    onValueChange = { folderName = it },
                    label = { Text("Folder Name") },
                    singleLine = true
                )
                Button(
                    onClick = {
                        if (folderName.isNotBlank()) {
                            onConfirm(folderName)
                            onDismiss()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outline)
                ) {
                    Text(text = "Create New Folder", color = MaterialTheme.colorScheme.scrim)
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderRow(
    icon: Int,
    textVal: String,
    noOfNotes: Int,
    isEnabled: Boolean,
    onDeleteClick: () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(enabled = !isEnabled) {}
            .background(color = MaterialTheme.colorScheme.inverseSurface)
            .height(40.dp)
            .border(
                width = 2.dp,
                brush = SolidColor(value = MaterialTheme.colorScheme.inverseOnSurface),
                shape = MaterialTheme.shapes.small
            )
            .padding(horizontal = 5.dp)
    ) {
        NoteFolderIcon(icon)
        NoteFolderNameText(folderName = textVal, modifier = Modifier.padding(start = 25.dp))
        Spacer(modifier = Modifier.weight(1f))
        if (!isEnabled) {
            NoteFolderNameText(folderName = noOfNotes.toString())
        } else {
            Icon(
                painter = painterResource(id = R.drawable.delete_icon),
                modifier = Modifier.clickable { onDeleteClick() },
                tint = MaterialTheme.colorScheme.scrim,
                contentDescription = ""
            )
        }
    }
}

