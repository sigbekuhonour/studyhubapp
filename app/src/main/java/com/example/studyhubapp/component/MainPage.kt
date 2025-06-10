package com.example.studyhubapp.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.studyhubapp.R
import com.example.studyhubapp.component.element.ActionsComp
import com.example.studyhubapp.component.element.AppButton
import com.example.studyhubapp.component.element.SimpleSearchBar
import com.example.studyhubapp.component.notefolder.FolderRow
import com.example.studyhubapp.viewmodel.AppViewModel

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


