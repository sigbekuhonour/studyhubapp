package com.example.studyhubapp.domain.datasource.local

import com.example.studyhubapp.domain.model.Folder
import com.example.studyhubapp.domain.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface LocalStorageDataSource {
    fun getAllFolders(): StateFlow<List<Folder>>
    fun getAllNotes(): StateFlow<List<Note>>
    suspend fun deleteFolderById(folderId: Int)
    suspend fun deleteNoteById(folderId: Int, noteId: Int)
    suspend fun addFolder(folder: Folder)
    suspend fun addNote(note: Note)
    suspend fun saveNoteChanges(
        folderId: Int,
        noteId: Int,
        title: String? = null,
        content: String? = null
    )

    suspend fun updateFolderName(folderId: Int, newFolderName: String)
}

class LocalStorageDataSourceImpl : LocalStorageDataSource {
    private val _folders = MutableStateFlow<List<Folder>>(
        listOf(
            Folder(id = 0, title = "Quick Notes"),
            Folder(id = 1, title = "Shared Notes"), Folder(id = 2, title = "Deleted Notes")
        )
    )
    val folders: StateFlow<List<Folder>> = _folders
    private val _notes = MutableStateFlow<List<Note>>(
        listOf(
            Note(id = 0, folderId = 0, title = "First Note", content = "First content"),
            Note(id = 1, folderId = 0, title = "Second Note", content = "Second content etc"),
            Note(id = 2, folderId = 1, title = "Third Note", content = "Third content etc"),
            Note(id = 3, folderId = 2, title = "Fourth Note", content = "Fourth content etc")
        )
    )
    val notes: StateFlow<List<Note>> = _notes

    override fun getAllFolders(): StateFlow<List<Folder>> {
        return folders
    }

    override fun getAllNotes(): StateFlow<List<Note>> {
        return notes
    }

    override suspend fun deleteFolderById(folderId: Int) {
        _folders.value = _folders.value.filterNot { it.id == folderId }
    }

    override suspend fun deleteNoteById(folderId: Int, noteId: Int) {
        _notes.value = _notes.value.filterNot { it.id == noteId && it.folderId == folderId }
    }

    override suspend fun addFolder(folder: Folder) {
        _folders.value = _folders.value + folder
    }

    override suspend fun addNote(note: Note) {
        _notes.value = _notes.value + note
    }

    override suspend fun saveNoteChanges(
        folderId: Int,
        noteId: Int,
        title: String?,
        content: String?
    ) {
        _notes.value = _notes.value.map { eachNote ->
            if (eachNote.folderId == folderId && eachNote.id == noteId) {
                eachNote.copy(
                    title = title ?: eachNote.title,
                    content = content ?: eachNote.content
                )
            } else eachNote
        }
    }

    override suspend fun updateFolderName(folderId: Int, newFolderName: String) {
        _folders.value = _folders.value.map { eachFolder ->
            if (eachFolder.id == folderId) {
                eachFolder.copy(title = newFolderName)
            } else {
                eachFolder
            }
        }
    }
}