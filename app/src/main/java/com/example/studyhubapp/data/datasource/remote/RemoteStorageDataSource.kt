package com.example.studyhubapp.data.datasource.remote

import com.example.studyhubapp.data.datasource.DataSource
import com.example.studyhubapp.domain.model.Folder
import com.example.studyhubapp.domain.model.Note
import kotlinx.coroutines.flow.StateFlow

class RemoteStorageDataSourceImpl : DataSource {
    override fun getAllFolders(): StateFlow<List<Folder>> {
        TODO("Not yet implemented")
    }

    override fun getAllNotes(): StateFlow<List<Note>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFolderById(folderId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNoteById(folderId: Int, noteId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun addFolder(folder: Folder) {
        TODO("Not yet implemented")
    }

    override suspend fun addNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun saveNoteChanges(
        folderId: Int,
        noteId: Int,
        title: String?,
        content: String?
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun updateFolderName(folderId: Int, newFolderName: String) {
        TODO("Not yet implemented")
    }

}