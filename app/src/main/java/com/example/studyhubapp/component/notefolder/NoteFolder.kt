package com.example.studyhubapp.component.notefolder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.studyhubapp.component.note.Note


data class NoteFolder(
    val id: Int,
    val name: String,
    val listOfNotes: SnapshotStateList<Note> = mutableStateListOf()
)

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
