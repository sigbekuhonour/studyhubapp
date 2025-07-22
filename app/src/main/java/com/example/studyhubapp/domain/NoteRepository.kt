package com.example.studyhubapp.domain

import com.example.studyhubapp.domain.model.Note

interface NoteRepository {
    suspend fun fetchNotes(folderId: Int): List<Note>
    suspend fun getNotes(): List<Note>
    suspend fun getNoteById(folderId: Int, noteId: Int): Note
    suspend fun addNoteByFolderId(folderId: Int, title: String)
    suspend fun deleteNoteByFolderId(folderId: Int, title: String)
}

class NoteRepositoryImpl() : NoteRepository {
    private val _notes = mutableListOf<Note>(
        Note(id = 0, folderId = 0, title = "First Note", content = "First content"),
        Note(id = 1, folderId = 0, title = "Second Note", content = "Second content etc"),
        Note(id = 2, folderId = 1, title = "Third Note", content = "Third content etc"),
        Note(id = 3, folderId = 2, title = "Fourth Note", content = "Fourth content etc")
    )

    override suspend fun getNotes(): List<Note> {
        return _notes
    }

    override suspend fun fetchNotes(folderId: Int): List<Note> {
        return _notes.filter { note -> note.folderId == folderId }
    }

    override suspend fun getNoteById(
        folderId: Int,
        noteId: Int
    ): Note {
        return _notes.first { note -> note.id == noteId && note.folderId == folderId }
    }

    override suspend fun addNoteByFolderId(folderId: Int, title: String) {
        val noteId = _notes.size
        _notes.add(element = Note(id = noteId, folderId = folderId, title = title))
    }

    override suspend fun deleteNoteByFolderId(folderId: Int, title: String) {
        _notes.removeIf { eachNote ->
            eachNote.folderId == folderId && eachNote.title == title
        }
    }
}