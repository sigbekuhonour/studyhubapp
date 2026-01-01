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
    private val localDataSourceImpl: LocalDataSource,
    private val remoteDataSourceImpl: RemoteDataSource,
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> =
        localDataSourceImpl.getAllNotes().distinctUntilChanged()

    override suspend fun getNoteId(folderId: String, title: String): String? =
        localDataSourceImpl.getAllNotes().first()
            .firstOrNull { note -> note.folderId == folderId && note.title == title }?.id


    override suspend fun addNoteByFolderId(folderId: String, title: String) {
        val noteId = UUID.randomUUID().toString()
        localDataSourceImpl.addNote(
            NoteEntity(
                noteId = noteId, ownerFolderId = folderId, title = title, content = ""
            )
        )
        remoteDataSourceImpl.addNote(
            note = Note(
                id = noteId, folderId = folderId, title = title
            )
        )
    }

    override suspend fun deleteNoteByFolderId(folderId: String, noteId: String) {
        localDataSourceImpl.deleteNoteById(folderId, noteId)
        remoteDataSourceImpl.deleteNoteById(folderId = folderId, noteId = noteId)
    }

    override suspend fun saveNoteChanges(
        folderId: String, noteId: String, title: String?, content: String?
    ) {
        localDataSourceImpl.saveNoteChanges(folderId, noteId, title, content)
        remoteDataSourceImpl.saveNoteChanges(folderId, noteId, title, content)
    }

    override suspend fun syncNotesFromRemote() {
        val remoteNotes = remoteDataSourceImpl.getAllNotes()
        if (remoteNotes.isNotEmpty()) {
            remoteNotes.forEach { note ->
                localDataSourceImpl.addNote(
                    note = NoteEntity(
                        noteId = note.id,
                        title = note.title,
                        content = note.content,
                        ownerFolderId = note.folderId,
                    )
                )
            }
        }

    }
}