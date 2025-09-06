package com.example.studyhubapp.ui.screens.note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studyhubapp.data.datasource.local.LocalStorageDataSourceProvider
import com.example.studyhubapp.data.repository.NoteRepositoryImpl
import com.example.studyhubapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private var _notes = noteRepository.getNotes()
    val notes: StateFlow<List<Note>>
        get() = _notes.map { noteList ->
            noteList.map { eachNote ->
                Note(
                    id = eachNote.id,
                    folderId = eachNote.folderId,
                    title = eachNote.title,
                    content = eachNote.content
                )
            }
        }.stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, emptyList())

    fun fetchNotesById(folderId: Int?): List<Note> {
        return notes.value.filter { eachNote -> eachNote.folderId == folderId }
    }

    fun getNoteById(
        folderId: Int,
        noteId: Int?
    ): Note {
        return notes.value.first { note -> note.id == noteId && note.folderId == folderId }
    }

    fun getNoteId(folderId: Int, title: String): Int? {
        return notes.value.firstOrNull { eachNote -> eachNote.folderId == folderId && eachNote.title == title }?.id
    }

    fun saveNoteChanges(
        folderId: Int,
        noteId: Int,
        title: String? = null,
        content: String? = null
    ) {
        viewModelScope.launch {
            noteRepository.saveNoteChanges(folderId, noteId, title, content)
        }
    }

    fun addNotesToFolderWithId(folderId: Int, title: String) {
        viewModelScope.launch {
            Log.i("NOTE_ADDED", "ADDED NOTES TO FOLDER ID $folderId")
            noteRepository.addNoteByFolderId(folderId = folderId, title = title)
        }
    }

    fun deleteNotesInFolderWithId(folderId: Int, noteId: Int) {
        viewModelScope.launch {
            noteRepository.deleteNoteByFolderId(folderId = folderId, noteId = noteId)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                NoteViewModel(
                    noteRepository = NoteRepositoryImpl(LocalStorageDataSourceProvider.instance)
                )
            }
        }
    }

}