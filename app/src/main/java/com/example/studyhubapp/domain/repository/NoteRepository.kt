package com.example.studyhubapp.domain.repository

import com.example.studyhubapp.domain.model.Note
import kotlinx.coroutines.flow.StateFlow

interface NoteRepository {
    suspend fun fetchNotes(folderId: Int): List<Note>
    fun getNotes(): StateFlow<List<Note>>
    suspend fun getNoteById(folderId: Int, noteId: Int): Note
    suspend fun addNoteByFolderId(folderId: Int, title: String)
    suspend fun deleteNoteByFolderId(folderId: Int, noteId: Int)
    suspend fun saveNoteChanges(
        folderId: Int,
        noteId: Int,
        title: String? = null,
        content: String? = null
    )
}
