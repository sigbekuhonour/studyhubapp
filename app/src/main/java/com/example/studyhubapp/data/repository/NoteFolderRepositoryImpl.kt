package com.example.studyhubapp.data.repository

import com.example.studyhubapp.data.datasource.local.LocalDataSource
import com.example.studyhubapp.data.datasource.remote.RemoteDataSource
import com.example.studyhubapp.domain.model.Folder
import com.example.studyhubapp.domain.repository.NoteFolderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class NoteFolderRepositoryImpl(
    private val localDataSourceImpl: LocalDataSource,
    private val remoteDataSourceImpl: RemoteDataSource
) :
    NoteFolderRepository {

    override fun getFolderContentSize(folderId: Int): Flow<Int> {
        return localDataSourceImpl.getAllNotes()
            .map { notes -> notes.count { it.folderId == folderId } }
    }

    override fun getAllFolders(): Flow<List<Folder>> {
        return localDataSourceImpl.getAllFolders()
    }

    override suspend fun addFolder(name: String) {
        val folderId = localDataSourceImpl.getAllFolders().first().size
        localDataSourceImpl.addFolder(Folder(id = folderId, title = name))
    }

    override suspend fun deleteFolder(folderId: Int) {
        localDataSourceImpl.deleteFolderById(folderId)
    }

    override suspend fun updateFolderName(folderId: Int, newFolderName: String) {
        localDataSourceImpl.updateFolderName(folderId, newFolderName = newFolderName)
    }
}