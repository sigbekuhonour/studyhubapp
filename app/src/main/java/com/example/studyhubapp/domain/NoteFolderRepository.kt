package com.example.studyhubapp.domain

import com.example.studyhubapp.domain.datasource.local.LocalStorageDataSource
import com.example.studyhubapp.domain.model.Folder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

interface NoteFolderRepository {

    suspend fun getFolder(id: Int): Folder
    fun getFolders(): StateFlow<List<Folder>>
    fun getFolderContentSize(folderId: Int): Flow<Int>
    suspend fun addFolder(name: String)
    suspend fun deleteFolder(folderId: Int)
}


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
}
