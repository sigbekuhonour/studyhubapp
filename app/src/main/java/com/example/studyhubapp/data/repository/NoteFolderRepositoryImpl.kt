package com.example.studyhubapp.data.repository

import com.example.studyhubapp.data.datasource.local.LocalStorageDataSource
import com.example.studyhubapp.domain.model.Folder
import com.example.studyhubapp.domain.repository.NoteFolderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class NoteFolderRepositoryImpl(private val dataSource: LocalStorageDataSource) :
    NoteFolderRepository {

    override suspend fun getFolder(id: Int): Folder {
        return dataSource.getAllFolders().value.first { it.id == id }
    }

    override fun getFolderContentSize(folderId: Int): Flow<Int> {
        return dataSource.getAllNotes()
            .map { notes -> notes.count { it.folderId == folderId } }
    }

    override fun getFolders(): StateFlow<List<Folder>> {
        return dataSource.getAllFolders()
    }

    override suspend fun addFolder(name: String) {
        val folderId = dataSource.getAllFolders().value.size
        dataSource.addFolder(Folder(id = folderId, title = name))
    }

    override suspend fun deleteFolder(folderId: Int) {
        dataSource.deleteFolderById(folderId)
    }

    override suspend fun updateFolderName(folderId: Int, newFolderName: String) {
        dataSource.updateFolderName(folderId, newFolderName = newFolderName)
    }
}