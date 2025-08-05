package com.example.studyhubapp.ui.note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Check
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studyhubapp.R
import com.example.studyhubapp.component.field.ContentTextField
import com.example.studyhubapp.component.field.TitleTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetail(
    folderName: String,
    folderId: Int,
    title: String,
    viewModel: NoteViewModel,
    navController: NavController
) {
    val note = viewModel.getNoteById(folderId, viewModel.getNoteId(folderId, title))
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            modifier = Modifier.clickable { navController.popBackStack() },
                            tint = MaterialTheme.colorScheme.scrim,
                            contentDescription = null
                        )
                        Text(
                            text = folderName,
                            color = MaterialTheme.colorScheme.scrim,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.saveNoteChanges(
                            folderId = folderId,
                            noteId = note.id,
                            title = title,
                            content = content
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Sharp.Check,
                            tint = MaterialTheme.colorScheme.scrim,
                            contentDescription = null
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.onPrimary)
            )
        },

        bottomBar = {
            BottomAppBar(
                contentColor = MaterialTheme.colorScheme.scrim,
                containerColor = MaterialTheme.colorScheme.onPrimary
            ) {

            }
        },
        containerColor = MaterialTheme.colorScheme.onPrimary
    ) { inPad ->
        Column(
            modifier = Modifier.padding(inPad),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            TitleTextField(
                title = title,
                onTitleChange = {
                    title = it
                })
            ContentTextField(
                content = content,
                onContentChange = {
                    content = it
                })

        }
    }
}
