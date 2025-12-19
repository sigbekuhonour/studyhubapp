package com.honoursigbeku.studyhubapp.ui.screens.notefolder

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NoteFolderRowText(folderName: String, modifier: Modifier = Modifier) {
    Text(text = folderName, modifier = modifier, color = MaterialTheme.colorScheme.onPrimary)
}