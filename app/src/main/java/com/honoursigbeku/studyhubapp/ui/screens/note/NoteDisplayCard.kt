package com.honoursigbeku.studyhubapp.ui.screens.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NoteDisplayCard(
    notes: List<Note>,
    deletedNotesFolderId: String,
    folderName: String, viewModel: NoteViewModel, navController: NavController
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.background(color = MaterialTheme.colorScheme.onPrimary)
    ) {
        items(items = notes) { eachNotes ->
            NoteRow(
                folderName = folderName,
                deletedNotesFolderId = deletedNotesFolderId,
                title = eachNotes.title,
                content = eachNotes.content,
                folderId = eachNotes.folderId,
                viewModel = viewModel,
                navController = navController,
            )
        }
    }
}