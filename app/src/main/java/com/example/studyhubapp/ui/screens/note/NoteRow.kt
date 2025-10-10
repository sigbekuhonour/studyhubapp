package com.example.studyhubapp.ui.screens.note

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studyhubapp.R

@Composable
fun NoteRow(
    title: String,
    folderName: String,
    content: String?,
    folderId: Int,
    viewModel: NoteViewModel,
    navController: NavController
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inverseSurface),
        onClick = { navController.navigate("Note/$folderName/$folderId/$title") }) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Menu,
                modifier = Modifier.padding(horizontal = 5.dp),
                tint = MaterialTheme.colorScheme.scrim,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = content ?: "",
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.delete_icon),
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clickable {
                        viewModel.getNoteId(folderId, title)
                            ?.let {
                                Log.d(
                                    "NoteViewModel",
                                    "deleteNotesInFolderWithId called: f=$folderId, id=$it"
                                )
                                viewModel.deleteNotesInFolderWithId(
                                    folderId = folderId,
                                    noteId = it
                                )
                            }
                    },
                tint = MaterialTheme.colorScheme.scrim,
                contentDescription = ""
            )
        }
    }

}

