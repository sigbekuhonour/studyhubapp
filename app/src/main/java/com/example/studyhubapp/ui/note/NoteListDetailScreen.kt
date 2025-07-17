package com.example.studyhubapp.ui.note

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.studyhubapp.R
import com.example.studyhubapp.component.searchbar.SimpleSearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListDetailScreen(viewModel: NoteViewModel, navController: NavController) {
    //i still have things to fix when it comes to state management
    val textFieldState = rememberTextFieldState()
    var searchResults by remember { mutableStateOf(listOf<String>()) }
    //
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            modifier = Modifier.clickable {
                                navController.navigate("LandingPage")
                            },
                            contentDescription = null
                        )
                        Text(
                            text = "Notes",
                            color = MaterialTheme.colorScheme.scrim,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.inverseSurface
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "No of notes is shown here")
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier.clickable {},
                        painter = painterResource(id = R.drawable.add_notes),
                        tint = MaterialTheme.colorScheme.scrim,
                        contentDescription = ""
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.onPrimary
    ) { inPad ->
        Column(modifier = Modifier.padding(inPad)) {
            SimpleSearchBar(
                textFieldState = textFieldState,
                searchResults = searchResults,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                onSearch = {})
            NoteDisplayCard(
                notes = viewModel.notes,
            )
        }
    }
}


@Preview
@Composable
private fun PrevNoteListScreen() {
    val viewModel = viewModel<NoteViewModel>()
    val navController = rememberNavController()
    NoteListDetailScreen(viewModel, navController)
}