package com.honoursigbeku.studyhubapp.ui.screens.note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.honoursigbeku.studyhubapp.R
import com.honoursigbeku.studyhubapp.ui.component.searchbar.SimpleSearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListDetailScreen(
    folderName: String,
    deletedNotesFolderId: String,
    folderId: String?, viewModel: NoteViewModel, navController: NavController
) {

    val textFieldState = rememberTextFieldState()
    val notes = viewModel.notes.collectAsState().value
    val noOfNotes = notes.filter { note -> note.folderId == folderId }.size
    val searchResults by remember {
        derivedStateOf {
            val query = textFieldState.text.toString()
            if (query.isEmpty()) {
                emptyList()
            } else {
                notes.filter { note ->
                    note.title.contains(query, ignoreCase = true)
                }.map { note ->
                    note.title
                }
            }
        }
    }

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
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        },
                        tint = MaterialTheme.colorScheme.scrim,
                        contentDescription = null
                    )
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }, bottomBar = {
            BottomAppBar(
                contentColor = MaterialTheme.colorScheme.scrim,
                containerColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "$noOfNotes note(s) in this folder")
                    Spacer(modifier = Modifier.weight(0.5f))
                    IconButton(
                        onClick = {
                            navController.navigate(
                                "notePage/$folderName/$folderId/New_Note${notes.filter { note -> note.folderId == folderId }.size}"
                            )
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_notes),
                            tint = MaterialTheme.colorScheme.scrim,
                            contentDescription = ""
                        )
                    }

                }
            }
        }, containerColor = MaterialTheme.colorScheme.onPrimary
    ) { inPad ->
        Column(
            modifier = Modifier.padding(inPad),
        ) {
            SimpleSearchBar(
                textFieldState = textFieldState,
                searchResults = searchResults,
                onSearch = { query ->
                    val firstResult = notes.firstOrNull {
                        it.title.contains(query, ignoreCase = true)
                    }
                    firstResult?.let { note ->
                        navController.navigate("notePage/$folderName/$folderId/${note.title}")
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                placeholderText = folderName,
                onResultClick = { title ->
                    val selectedNote = notes.firstOrNull { it.title == title }
                    selectedNote?.let { note ->
                        navController.navigate("notePage/$folderName/$folderId/${note.title}")
                    }
                })
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            NoteDisplayCard(
                folderName = folderName,
                deletedNotesFolderId = deletedNotesFolderId,
                notes = notes.filter { note -> note.folderId == folderId },
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}


