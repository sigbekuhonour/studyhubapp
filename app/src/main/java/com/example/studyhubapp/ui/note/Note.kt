package com.example.studyhubapp.ui.note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.studyhubapp.ui.notefolder.NoteFolderViewModel

data class Note(
    val id: Int,
    val folderId: Int?,
    val title: String,
    val content: String?
)

@Composable
fun ListOfNotesScreen(viewModel: NoteFolderViewModel) {
    Scaffold(
        topBar = TODO(),
        bottomBar = TODO(),
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) { inPad ->
        Column(modifier = Modifier.padding(inPad)) {
            Text(
                text = "List of Notes",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
