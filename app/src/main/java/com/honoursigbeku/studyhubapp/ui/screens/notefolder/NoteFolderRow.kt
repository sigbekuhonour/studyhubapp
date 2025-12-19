package com.honoursigbeku.studyhubapp.ui.screens.notefolder

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.honoursigbeku.studyhubapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderRow(
    icon: Int,
    folderId: Int,
    folderName: String,
    noteFolderContentSize: Int,
    isActionTextClicked: Boolean,
    navController: NavController,
    onDeleteClick: () -> Unit
) {

    Log.d("FolderRow", "FolderDto $folderId noteCount: $noteFolderContentSize")
    var expanded by rememberSaveable { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(enabled = !isActionTextClicked) {
                navController.navigate("noteListPage/$folderName/$folderId")
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
        NoteFolderRowText(folderName = folderName, modifier = Modifier.padding(start = 25.dp))
        Spacer(modifier = Modifier.weight(1f))
        if (!isActionTextClicked) {
            NoteFolderRowText(folderName = noteFolderContentSize.toString())

            Box {
                Icon(
                    painterResource(id = R.drawable.more_vert),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.clickable { expanded = !expanded },
                    contentDescription = "More options"
                )
                DropdownMenu(
                    expanded = expanded,
                    shape = MaterialTheme.shapes.large,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Rename FolderDto") },
                        onClick = {
                            expanded = false
                            navController.navigate("renameFolderPage/$folderId/$folderName")
                        }
                    )
                }
            }
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