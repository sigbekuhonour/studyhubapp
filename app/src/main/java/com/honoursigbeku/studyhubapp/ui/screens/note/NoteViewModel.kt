package com.honoursigbeku.studyhubapp.ui.screens.note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.honoursigbeku.studyhubapp.data.datasource.local.LocalDataSource
import com.honoursigbeku.studyhubapp.data.datasource.remote.RemoteDataSource
import com.honoursigbeku.studyhubapp.data.repository.NoteRepositoryImpl
import com.honoursigbeku.studyhubapp.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    private val _notes: StateFlow<List<Note>> = noteRepository.getAllNotes().map { notes ->
        notes.map { eachNote ->
            Note(
                id = eachNote.id,
                folderId = eachNote.folderId,
                title = eachNote.title,
                content = eachNote.content
            )
        }
    }.distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val notes: StateFlow<List<Note>> = _notes

    fun getNoteByTitle(
        folderId: String, title: String
    ): StateFlow<Note?> =
        notes.map { list -> list.firstOrNull { it.folderId == folderId && it.title == title } }
            .distinctUntilChanged()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    suspend fun getNoteId(folderId: String, title: String): String? {
        return withContext(Dispatchers.IO) {
            noteRepository.getNoteId(folderId, title)
        }
    }

    fun initializeNote(folderId: String, title: String) {
        viewModelScope.launch {
            val noteId = getNoteId(folderId, title)
            if (noteId == null) {
                Log.d("NoteViewModel", "Creating new note: $title in folder $folderId")
                addNotesToFolderWithId(folderId, title)
            } else {
                Log.d("NoteViewModel", "NoteDto already exists: $noteId")
            }
        }
    }

    fun saveNoteChanges(
        folderId: String, noteId: String, title: String? = null, content: String? = null
    ) {
        viewModelScope.launch {
            noteRepository.saveNoteChanges(folderId, noteId, title, content)
        }
    }

    fun addNotesToFolderWithId(folderId: String, title: String) {
        viewModelScope.launch {
            Log.i("NOTE_ADDED", "ADDED NOTES TO FOLDER ID $folderId")
            noteRepository.addNoteByFolderId(folderId = folderId, title = title)
        }
    }

    fun deleteNotesInFolderWithId(folderId: String, noteId: String) {
        viewModelScope.launch {
            noteRepository.deleteNoteByFolderId(folderId = folderId, noteId = noteId)
        }
    }

    companion object {
        fun Factory(
            localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                NoteViewModel(
                    noteRepository = NoteRepositoryImpl(
                        localDataSourceImpl = localDataSource,
                        remoteDataSourceImpl = remoteDataSource
                    )
                )
            }
        }
    }

}