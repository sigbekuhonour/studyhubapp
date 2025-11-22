package com.example.studyhubapp.data.repository

import com.example.studyhubapp.data.datasource.local.LocalDataSource
import com.example.studyhubapp.data.datasource.remote.RemoteDataSource
import com.example.studyhubapp.domain.model.Note
import com.example.studyhubapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first

class NoteRepositoryImpl(
    private val localDataSourceImpl: LocalDataSource,
    private val remoteDataSourceImpl: RemoteDataSource,
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> =
        localDataSourceImpl.getAllNotes().distinctUntilChanged()

    override suspend fun getNoteId(folderId: Int, title: String): Int? =
        localDataSourceImpl.getAllNotes().first()
            .firstOrNull { note -> note.folderId == folderId && note.title == title }?.id


    override suspend fun addNoteByFolderId(folderId: Int, title: String) {
        val noteId = localDataSourceImpl.getAllNotes().first().size
        localDataSourceImpl.addNote(Note(id = noteId, folderId = folderId, title = title))
    }

    override suspend fun deleteNoteByFolderId(folderId: Int, noteId: Int) {
        localDataSourceImpl.deleteNoteById(folderId, noteId)
    }

    override suspend fun saveNoteChanges(
        folderId: Int,
        noteId: Int,
        title: String?,
        content: String?
    ) {
        localDataSourceImpl.saveNoteChanges(folderId, noteId, title, content)
    }
}