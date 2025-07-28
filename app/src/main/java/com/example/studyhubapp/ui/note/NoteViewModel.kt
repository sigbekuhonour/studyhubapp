package com.example.studyhubapp.ui.note

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studyhubapp.domain.NoteRepository
import com.example.studyhubapp.domain.NoteRepositoryImpl
import com.example.studyhubapp.domain.datasource.local.LocalStorageDataSourceImpl
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private var _notes = mutableStateListOf<Note>()
    private fun loadData() {
        viewModelScope.launch {
            try {
                val defaultNotes = noteRepository.getNotes()
                if (defaultNotes != _notes.toList()) {
                    defaultNotes.forEach { eachNotes ->
                        _notes.add(
                            Note(
                                id = eachNotes.id,
                                folderId = eachNotes.folderId,
                                title = eachNotes.title,
                                content = eachNotes.content
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                println("Error loading data")
            }
        }
    }

    init {
        loadData()
    }

    val notes: List<Note> get() = _notes

    fun fetchNotesById(folderId: Int?): List<Note> {
        return _notes.filter { eachNote -> eachNote.folderId == folderId }
    }

    fun getNoteById(
        folderId: Int,
        noteId: Int
    ): Note {
        return _notes.first { note -> note.id == noteId && note.folderId == folderId }
    }

    fun addNotesToFolderWithId(folderId: Int, title: String) {
        viewModelScope.launch {
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
                    noteRepository = NoteRepositoryImpl(LocalStorageDataSourceImpl())
                )
            }
        }
    }

}