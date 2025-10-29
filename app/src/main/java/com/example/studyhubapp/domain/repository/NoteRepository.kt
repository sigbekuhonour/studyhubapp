package com.example.studyhubapp.domain.repository

import com.example.studyhubapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteId(folderId: Int, title: String): Int?
    suspend fun addNoteByFolderId(folderId: Int, title: String)
    suspend fun deleteNoteByFolderId(folderId: Int, noteId: Int)
    suspend fun saveNoteChanges(
        folderId: Int,
        noteId: Int,
        title: String? = null,
        content: String? = null
    )
}
