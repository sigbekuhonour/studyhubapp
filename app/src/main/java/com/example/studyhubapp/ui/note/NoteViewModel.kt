package com.example.studyhubapp.ui.note

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class NoteViewModel(initialNotes: SnapshotStateList<Note>) : ViewModel() {
    private val _notes = initialNotes
    val notes: List<Note> get() = _notes
}