package com.example.studyhubapp.ui.notefolder

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.studyhubapp.R
import com.example.studyhubapp.ui.note.Note


data class NoteFolder(
    val id: Int,
    val icon: Int = R.drawable.folder_icon,
    val name: String,
    val listOfNotes: SnapshotStateList<Note> = mutableStateListOf()
)




