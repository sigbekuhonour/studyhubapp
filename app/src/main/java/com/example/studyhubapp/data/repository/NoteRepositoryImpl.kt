package com.example.studyhubapp.data.repository

import com.example.studyhubapp.data.datasource.DataSource
import com.example.studyhubapp.domain.model.Note
import com.example.studyhubapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first

class NoteRepositoryImpl(private val dataSourceImpl: DataSource) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> =
        dataSourceImpl.getAllNotes().distinctUntilChanged()

    override suspend fun addNoteByFolderId(folderId: Int, title: String) {
        val noteId = dataSourceImpl.getAllNotes().first().size
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