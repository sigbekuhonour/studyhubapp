package com.example.studyhubapp.domain

import com.example.studyhubapp.domain.datasource.local.LocalStorageDataSource
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

class NoteRepositoryImpl(private val dataSourceImpl: LocalStorageDataSource) : NoteRepository {


    override fun getNotes(): StateFlow<List<Note>> {
        return dataSourceImpl.getAllNotes()
    }

    override suspend fun fetchNotes(folderId: Int): List<Note> {
        return dataSourceImpl.getAllNotes().value.filter { note -> note.folderId == folderId }
    }

    override suspend fun getNoteById(
        folderId: Int,
        noteId: Int
    ): Note {
        return dataSourceImpl.getAllNotes().value
            .first { note -> note.id == noteId && note.folderId == folderId }
    }

    override suspend fun addNoteByFolderId(folderId: Int, title: String) {
        val noteId = dataSourceImpl.getAllNotes().value.size
        dataSourceImpl.addNote(Note(id = noteId, folderId = folderId, title = title))
    }

    override suspend fun deleteNoteByFolderId(folderId: Int, noteId: Int) {
        dataSourceImpl.deleteNoteById(folderId, noteId)
    }

    override suspend fun saveNoteChanges(
        folderId: Int,
        noteId: Int,
        title: String?,
        content: String?
    ) {
        dataSourceImpl.saveNoteChanges(folderId, noteId, title, content)
    }
}