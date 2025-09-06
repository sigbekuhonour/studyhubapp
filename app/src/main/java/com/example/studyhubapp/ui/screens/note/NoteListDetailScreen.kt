package com.example.studyhubapp.ui.screens.note

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
import com.example.studyhubapp.ui.component.searchbar.SimpleSearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListDetailScreen(
    folderName: String,
    folderId: Int?,
    viewModel: NoteViewModel,
    navController: NavController
) {

    val textFieldState = rememberTextFieldState()
    val noOfNotes = viewModel.fetchNotesById(folderId).size
    var searchResults by remember { mutableStateOf(listOf<String>()) }
    //
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
                            modifier = Modifier.clickable {
                                navController.popBackStack()
                            },
                            tint = MaterialTheme.colorScheme.scrim,
                            contentDescription = null
                        )
                        Text(
                            text = folderName,
                            color = MaterialTheme.colorScheme.scrim,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
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
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier.clickable {
                            navController.navigate(
                                "Note/$folderName/$folderId/New_Note"
                            )
                        },
                        painter = painterResource(id = R.drawable.add_notes),
                        tint = MaterialTheme.colorScheme.scrim,
                        contentDescription = ""
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.onPrimary
    ) { inPad ->
        Column(
            modifier = Modifier.padding(inPad),
        ) {
            SimpleSearchBar(
                textFieldState = textFieldState,
                searchResults = searchResults,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                onSearch = {})
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            NoteDisplayCard(
                folderName = folderName,
                notes = viewModel.fetchNotesById(folderId), navController = navController
            )
        }
    }
}


