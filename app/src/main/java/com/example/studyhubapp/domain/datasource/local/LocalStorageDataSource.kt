package com.example.studyhubapp.domain.datasource.local

import com.example.studyhubapp.domain.model.Folder
import com.example.studyhubapp.domain.model.Note

interface LocalStorageDataSource {
    suspend fun getAllFolders(): List<Folder>
    suspend fun deleteFolderById(folderId: Int)
    suspend fun addFolder(folder: Folder)
    suspend fun getAllNotes(): List<Note>
    suspend fun deleteNoteById(folderId: Int, noteId: Int)
    suspend fun addNote(note: Note)
}

class LocalStorageDataSourceImpl : LocalStorageDataSource {
    private val _folders = mutableListOf<Folder>(
        Folder(id = 0, title = "Quick Notes"),
        Folder(id = 1, title = "Shared Notes"), Folder(id = 2, title = "Deleted Notes")
    )

    private val _notes = mutableListOf<Note>(
        Note(id = 0, folderId = 0, title = "First Note", content = "First content"),
        Note(id = 1, folderId = 0, title = "Second Note", content = "Second content etc"),
        Note(id = 2, folderId = 1, title = "Third Note", content = "Third content etc"),
        Note(id = 3, folderId = 2, title = "Fourth Note", content = "Fourth content etc")
    )

    override suspend fun getAllFolders(): List<Folder> {
        return _folders
    }

    override suspend fun deleteFolderById(folderId: Int) {
        _folders.removeIf { folder -> folder.id == folderId }
    }

    override suspend fun addFolder(folder: Folder) {
        _folders.add(folder)
    }

    override suspend fun getAllNotes(): List<Note> {
        return _notes
    }

    override suspend fun deleteNoteById(folderId: Int, noteId: Int) {
        _notes.removeIf { note -> note.id == noteId && note.folderId == folderId }
    }

    override suspend fun addNote(note: Note) {
        _notes.add(note)
    }
}