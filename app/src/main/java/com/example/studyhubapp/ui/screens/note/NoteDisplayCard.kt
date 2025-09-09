package com.example.studyhubapp.ui.screens.note

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun NoteDisplayCard(
    notes: List<Note>,
    folderName: String,
    viewModel: NoteViewModel,
    navController: NavController
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(color = MaterialTheme.colorScheme.inverseSurface)
    ) {
        items(items = notes) { eachNotes ->
            NoteRow(
                folderName = folderName,
                title = eachNotes.title,
                content = eachNotes.content,
                folderId = eachNotes.folderId,
                viewModel = viewModel,
                navController = navController
            )
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        }
    }
}