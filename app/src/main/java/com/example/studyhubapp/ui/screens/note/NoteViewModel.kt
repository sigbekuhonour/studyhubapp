package com.example.studyhubapp.ui.screens.note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studyhubapp.data.datasource.DataSource
import com.example.studyhubapp.data.repository.NoteRepositoryImpl
import com.example.studyhubapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    private val _notes: StateFlow<List<Note>> =
        noteRepository.getAllNotes().map { notes ->
            notes.map { eachNote ->
                Note(
                    id = eachNote.id,
                    folderId = eachNote.folderId,
                    title = eachNote.title,
                    content = eachNote.content
                )
            }
        }
            .distinctUntilChanged()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val notes: StateFlow<List<Note>> = _notes

    fun getNoteByTitle(
        folderId: Int,
        title: String
    ): StateFlow<Note?> =
        notes
            .map { list -> list.firstOrNull { it.folderId == folderId && it.title == title } }
            .distinctUntilChanged()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

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
        fun Factory(dataSource: DataSource): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    NoteViewModel(
                        noteRepository = NoteRepositoryImpl(dataSource)
                    )
                }
            }
    }

}