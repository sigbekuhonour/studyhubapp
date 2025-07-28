package com.example.studyhubapp.domain

import com.example.studyhubapp.domain.datasource.local.LocalStorageDataSource
import com.example.studyhubapp.domain.model.Folder

interface NoteFolderRepository {

    suspend fun getFolder(id: Int): Folder
    suspend fun getFolders(): List<Folder>
    suspend fun getFolderContentSize(folderId: Int): Int
    suspend fun addFolder(name: String)
    suspend fun deleteFolder(folderId: Int)
}


class NoteFolderRepositoryImpl(private val dataSource: LocalStorageDataSource) :
    NoteFolderRepository {

    override suspend fun getFolder(id: Int): Folder {
        return dataSource.getAllFolders()[id]
    }

    override suspend fun getFolderContentSize(folderId: Int): Int {
        return dataSource.getAllNotes().filter { note -> note.folderId == folderId }.size
    }

    override suspend fun getFolders(): List<Folder> {
        return dataSource.getAllFolders()
    }

    override suspend fun addFolder(name: String) {
        val folderId = dataSource.getAllFolders().size
        dataSource.addFolder(Folder(id = folderId, title = name))
    }

    override suspend fun deleteFolder(folderId: Int) {
        dataSource.deleteFolderById(folderId)
    }
}
