package com.honoursigbeku.studyhubapp.ui.screens.note

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.honoursigbeku.studyhubapp.R
import com.honoursigbeku.studyhubapp.ui.component.field.ContentTextField
import com.honoursigbeku.studyhubapp.ui.component.field.TitleTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditorScreen(
    folderName: String,
    folderId: String,
    title: String,
    viewModel: NoteViewModel,
    navController: NavController
) {
    val note = viewModel.getNoteByTitle(folderId, title).collectAsState().value
    var title by rememberSaveable { mutableStateOf("") }
    var content: String? by rememberSaveable { mutableStateOf("") }
    if (note != null) {
        title = note.title
        content = note.content
    }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = folderName,
                        color = MaterialTheme.colorScheme.scrim,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }, navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        modifier = Modifier.clickable { navController.popBackStack() },
                        tint = MaterialTheme.colorScheme.scrim,
                        contentDescription = null
                    )
                }, actions = {
                    IconButton(onClick = {
                        Log.i(
                            "NoteEditorScreen",
                            "These are the contents of notes before save${viewModel.notes.value}"
                        )
                        note?.let {
                            viewModel.saveNoteChanges(
                                folderId = folderId,
                                noteId = it.id,
                                title = title,
                                content = content
                            )
                        }
                        Log.i(
                            "NoteEditorScreen",
                            "These are the contents of notes after save${viewModel.notes.value}"
                        )
                        Toast.makeText(
                            context, "Successfully saved note details", Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.check),
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
        }, containerColor = MaterialTheme.colorScheme.onPrimary
    ) { inPad ->
        Column(
            modifier = Modifier.padding(inPad), verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(painter = painterResource(id = R.drawable.favorite), contentDescription = null)
                Text(
                    text = "Review Cards",
                    modifier = Modifier
                        .clickable { navController.navigate("flashcardPage/${note?.id}") }
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.onSurfaceVariant,
                                    MaterialTheme.colorScheme.onPrimary,
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        ))
            }

            TitleTextField(
                title = title, onTitleChange = {
                    title = it
                })
            ContentTextField(
                content = content, onContentChange = {
                    content = it
                })


        }
    }
}
