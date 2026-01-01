package com.honoursigbeku.studyhubapp.domain.repository

import com.honoursigbeku.studyhubapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteId(folderId: String, title: String): String?
    suspend fun addNoteByFolderId(folderId: String, title: String)
    suspend fun deleteNoteByFolderId(folderId: String, noteId: String)
    suspend fun saveNoteChanges(
        folderId: String,
        noteId: String,
        title: String? = null,
        content: String? = null
    )

    suspend fun syncNotesFromRemote()
}
