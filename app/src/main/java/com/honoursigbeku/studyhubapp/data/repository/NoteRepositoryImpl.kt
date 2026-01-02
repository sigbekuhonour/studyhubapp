package com.honoursigbeku.studyhubapp.data.repository

import com.honoursigbeku.studyhubapp.data.datasource.local.LocalDataSource
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.NoteEntity
import com.honoursigbeku.studyhubapp.data.datasource.remote.RemoteDataSource
import com.honoursigbeku.studyhubapp.domain.model.Note
import com.honoursigbeku.studyhubapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import java.util.UUID

class NoteRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> =
        localDataSource.getAllNotes().distinctUntilChanged()

    override suspend fun getNoteId(folderId: String, title: String): String? =
        localDataSource.getAllNotes().first()
            .firstOrNull { note -> note.folderId == folderId && note.title == title }?.id


    override suspend fun addNoteByFolderId(folderId: String, title: String) {
        val noteId = UUID.randomUUID().toString()
        localDataSource.addNote(
            NoteEntity(
                noteId = noteId, ownerFolderId = folderId, title = title, content = ""
            )
        )
        remoteDataSource.addNote(
            note = Note(
                id = noteId, folderId = folderId, title = title
            )
        )
    }

    override suspend fun deleteNoteByFolderId(folderId: String, noteId: String) {
        localDataSource.deleteNoteById(folderId, noteId)
        remoteDataSource.deleteNoteById(folderId = folderId, noteId = noteId)
    }

    override suspend fun saveNoteChanges(
        folderId: String, noteId: String, title: String?, content: String?
    ) {
        localDataSource.saveNoteChanges(folderId, noteId, title, content)
        remoteDataSource.saveNoteChanges(folderId, noteId, title, content)
    }

}