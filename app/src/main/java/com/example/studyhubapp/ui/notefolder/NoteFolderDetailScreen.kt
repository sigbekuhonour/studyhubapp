package com.example.studyhubapp.ui.notefolder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.studyhubapp.R
import com.example.studyhubapp.component.icons.BottomAppBarIcon
import com.example.studyhubapp.component.searchbar.SimpleSearchBar


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NoteFolderDetailScreen(
    noteFolderViewModel: NoteFolderViewModel,
    navController: NavController
) {
    val lazyColumnState = rememberLazyListState()
    var newFolderButtonIsClicked by rememberSaveable { mutableStateOf(false) }
    var isEnabled by rememberSaveable { mutableStateOf(false) }
    var actionText by rememberSaveable { mutableStateOf("") }
    actionText = if (isEnabled) {
        "Done"
    } else {
        "Edit"
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val textFieldState = rememberTextFieldState()
    var searchResults by remember { mutableStateOf(listOf<String>()) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Folders",
                        color = MaterialTheme.colorScheme.scrim,
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                modifier = Modifier.padding(horizontal = 5.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    Text(
                        text = actionText,
                        color = MaterialTheme.colorScheme.scrim,
                        modifier = Modifier.clickable {
                            isEnabled = !isEnabled
                        },
                        fontSize = 20.sp
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = MaterialTheme.colorScheme.onPrimary, bottomBar = {
            BottomAppBar(
                modifier = Modifier.padding(5.dp),
                tonalElevation = 2.dp,
                containerColor = MaterialTheme.colorScheme.onPrimary,
                content = {
                    BottomAppBarIcon(
                        icon = R.drawable.new_folder,
                        onClick = { newFolderButtonIsClicked = !newFolderButtonIsClicked })
                    Spacer(modifier = Modifier.weight(1f))
                    BottomAppBarIcon(icon = R.drawable.new_notes)
                }
            )
        }) { inPad ->
        Column(
            modifier = Modifier
                .padding(inPad)
                .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SimpleSearchBar(
                textFieldState = textFieldState,
                searchResults = searchResults,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                onSearch = {})
            if (newFolderButtonIsClicked) {
                CreateNewFolderDialog(
                    onDismiss = { newFolderButtonIsClicked = !newFolderButtonIsClicked },
                    onConfirm = { newFolderName ->
                        noteFolderViewModel.addFolder(newFolderName)
                    }
                )
            }
            LazyColumn(
                state = lazyColumnState,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(noteFolderViewModel.folders) { eachFolder ->
                    FolderRow(
                        folderId = eachFolder.id,
                        icon = eachFolder.icon,
                        textVal = eachFolder.name,
                        noOfNotes = noteFolderViewModel.getFolderContentSize(eachFolder.id),
                        isEnabled = isEnabled,
                        navController = navController
                    ) { noteFolderViewModel.deleteFolder(eachFolder.name) }
                }

            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}


