package com.example.studyhubapp.ui.notefolder

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studyhubapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderRow(
    icon: Int,
    folderId: Int,
    textVal: String,
    noOfNotes: Int,
    isEnabled: Boolean,
    navController: NavController,
    onDeleteClick: () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(enabled = !isEnabled) {
                navController.navigate("NoteList/$textVal/$folderId")
            }

            .background(color = MaterialTheme.colorScheme.inverseSurface)
            .height(40.dp)
            .border(
                width = 2.dp,
                brush = SolidColor(value = MaterialTheme.colorScheme.inverseOnSurface),
                shape = MaterialTheme.shapes.small
            )
            .padding(horizontal = 15.dp)
    ) {
        NoteFolderRowIcon(icon)
        NoteFolderRowText(folderName = textVal, modifier = Modifier.padding(start = 25.dp))
        Spacer(modifier = Modifier.weight(1f))
        if (!isEnabled) {
            NoteFolderRowText(folderName = noOfNotes.toString())
        } else {
            Icon(
                painter = painterResource(id = R.drawable.delete_icon),
                modifier = Modifier.clickable { onDeleteClick() },
                tint = MaterialTheme.colorScheme.scrim,
                contentDescription = ""
            )
        }
    }
}