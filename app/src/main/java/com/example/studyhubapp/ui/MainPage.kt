package com.example.studyhubapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studyhubapp.R
import com.example.studyhubapp.component.bottomappbar.BottomAppBar
import com.example.studyhubapp.component.notefolder.FolderRow
import com.example.studyhubapp.component.searchbar.SimpleSearchBar
import com.example.studyhubapp.component.topappbar.ActionsComp
import com.example.studyhubapp.component.topappbar.TopAppBar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainPageUI(viewModel: AppViewModel) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val textFieldState = rememberTextFieldState()
    var searchResults by remember { mutableStateOf(listOf<String>()) }
    Scaffold(
        topBar = {
            TopAppBar(
                topAppBarText = "Notes",
                actions = { ActionsComp() },
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = MaterialTheme.colorScheme.onTertiaryContainer, bottomBar = {
            BottomAppBar()
        }) { inPad ->
        Column(
            modifier = Modifier.padding(inPad),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            //you've got work here boy
            SimpleSearchBar(
                textFieldState = textFieldState,
                searchResults = searchResults,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                onSearch = {})
            FolderRow(R.drawable.folder_icon, textVal = "Quick Note", noOfNotes = "0")
            FolderRow(R.drawable.shared_folder, textVal = "Shared notes", noOfNotes = "0")
            FolderRow(R.drawable.deleted_folder, textVal = "Recently Deleted", noOfNotes = "0")
            Spacer(modifier = Modifier.weight(1f))

        }
    }
}


