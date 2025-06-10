package com.example.studyhubapp.appUI

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyhubapp.R
import com.example.studyhubapp.viewModel.AppViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainPageUI(viewModel: AppViewModel) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val textFieldState = rememberTextFieldState()
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Folders", fontWeight = FontWeight.Bold) },

            actions = { ActionsComp() },
            scrollBehavior = scrollBehavior
        )
    }, bottomBar = {
        BottomAppBar(
            tonalElevation = 2.dp,
            content = {
                AppButton(icon = R.drawable.new_folder)
                Spacer(modifier = Modifier.weight(1f))
                AppButton(icon = R.drawable.new_notes)
            }
        )
    }) { inPad ->
        Column(
            modifier = Modifier.padding(inPad),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            //youve got work here boy
            SimpleSearchBar(
                textFieldState = textFieldState,
                onSearch = { /* implement search logic here */ },
                searchResults = emptyList(), // or your actual list of results
            )
            FolderRow(R.drawable.folder_icon, textVal = "Quick Note", noOfNotes = "0")
            FolderRow(R.drawable.shared_folder, textVal = "Shared notes", noOfNotes = "0")
            FolderRow(R.drawable.deleted_folder, textVal = "Recently Deleted", noOfNotes = "0")
            Spacer(modifier = Modifier.weight(1f))

        }
    }
}


@Composable
fun AppButton(modifier: Modifier = Modifier, icon: Int) {
    IconButton(onClick = {}) {
        Icon(painter = painterResource(id = icon), contentDescription = null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderRow(icon: Int, textVal: String, noOfNotes: String) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .background(color = Color.LightGray)
            .fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.Start) {
            Icon(painter = painterResource(id = icon), tint = null, contentDescription = "")
            Text(text = textVal, modifier = Modifier.padding(start = 25.dp))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = noOfNotes)
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSearchBar(
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    modifier: Modifier = Modifier
) {
    // Controls expansion state of the search bar
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier
            .padding(start = 20.dp)
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                    onSearch = {
                        onSearch(textFieldState.text.toString())
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Search") }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            // Display search results in a scrollable column
            Column(Modifier.verticalScroll(rememberScrollState())) {
                searchResults.forEach { result ->
                    ListItem(
                        headlineContent = { Text(result) },
                        modifier = Modifier
                            .clickable {
                                textFieldState.edit { replace(0, length, result) }
                                expanded = false
                            }
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}


@Composable
fun ActionsComp(modifier: Modifier = Modifier) {
    var isEnabled by rememberSaveable { mutableStateOf(false) }
    var actionText by rememberSaveable { mutableStateOf("Edit") }

    Button(onClick = {

    }, colors = ButtonDefaults.buttonColors(Color.Transparent)) {
        Row {
            Text(text = actionText, color = Color(0xFFD9C312), fontSize = 20.sp)
        }
    }
}