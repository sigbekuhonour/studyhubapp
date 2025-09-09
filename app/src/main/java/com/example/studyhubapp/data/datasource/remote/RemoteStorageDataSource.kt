package com.example.studyhubapp.data.datasource.remote

import com.example.studyhubapp.data.datasource.DataSource
import com.example.studyhubapp.data.datasource.remote.dao.FolderDao
import com.example.studyhubapp.data.datasource.remote.dao.NoteDao
import com.example.studyhubapp.domain.model.Folder
import com.example.studyhubapp.domain.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RemoteStorageDataSourceImpl(
    private val folderDao: FolderDao,
    private val noteDao: NoteDao,
    private val scope: CoroutineScope
) : DataSource {
    override fun getAllFolders(): StateFlow<List<Folder>> =
        folderDao.getAllFolders()
            .map { daoFolders ->
                daoFolders.map { eachFolder ->
                    Folder(id = eachFolder.folderId, title = eachFolder.title)
                }
            }.stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )


    override fun getAllNotes(): StateFlow<List<Note>> =
        noteDao.getAllNotes()
            .map { daoNotes ->
                daoNotes.map { eachNote ->
                    Note(
                        id = eachNote.noteId,
                        title = eachNote.title,
                        content = eachNote.content,
                        folderId = eachNote.ownerFolderId
                    )
                }
            }.stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )


    override suspend fun deleteFolderById(folderId: Int) {
        folderDao.deleteFolderById(folderId)
    }

    override suspend fun deleteNoteById(folderId: Int, noteId: Int) {
        noteDao.deleteNoteById(folderId, noteId)
    }

    override suspend fun addFolder(folder: Folder) {

    }

    override suspend fun addNote(note: Note) {

    }

    override suspend fun saveNoteChanges(
        folderId: Int,
        noteId: Int,
        title: String?,
        content: String?
    ) {
        noteDao.saveNoteChanges(folderId, noteId, title, content)
    }

    override suspend fun updateFolderName(folderId: Int, newFolderName: String) {
        folderDao.updateFolderName(newFolderName, folderId)
    }

}