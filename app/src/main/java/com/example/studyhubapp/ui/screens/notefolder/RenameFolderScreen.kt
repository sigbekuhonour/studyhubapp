package com.example.studyhubapp.ui.screens.notefolder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studyhubapp.ui.component.field.TitleTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenameFolderScreen(
    folderId: Int,
    navController: NavController,
    currentFolderName: String,
    viewModel: NoteFolderViewModel
) {
    var folderName by rememberSaveable { mutableStateOf(currentFolderName) }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Folders") },
            colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.onPrimary),
            navigationIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.TwoTone.ArrowBack,
                    modifier = Modifier.clickable { navController.popBackStack() },
                    contentDescription = null
                )
            }, scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        )
    }, containerColor = MaterialTheme.colorScheme.onPrimary) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Input new folder name")
            TitleTextField(title = folderName) { folderName = it }
            Button(
                onClick = { viewModel.updateFolderName(folderId, currentFolderName = folderName) },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                Text(text = "Confirm changes", color = MaterialTheme.colorScheme.scrim)
            }
        }

    }
}