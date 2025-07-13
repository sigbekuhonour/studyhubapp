package com.example.studyhubapp.ui.notefolder

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteFolderIcon(id: Int, modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = id),
        tint = MaterialTheme.colorScheme.scrim,
        contentDescription = ""
    )
}