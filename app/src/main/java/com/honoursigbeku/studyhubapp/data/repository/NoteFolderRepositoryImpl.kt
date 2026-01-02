package com.honoursigbeku.studyhubapp.data.repository

import com.honoursigbeku.studyhubapp.data.datasource.local.LocalDataSource
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FolderEntity
import com.honoursigbeku.studyhubapp.data.datasource.remote.RemoteDataSource
import com.honoursigbeku.studyhubapp.domain.model.Folder
import com.honoursigbeku.studyhubapp.domain.repository.NoteFolderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteFolderRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : NoteFolderRepository {

    override fun getFolderContentSize(folderId: String): Flow<Int> {
        return localDataSource.getAllNotes()
            .map { notes -> notes.count { it.folderId == folderId } }
    }

    override fun getAllFolders(userId: String): Flow<List<Folder>> {
        return localDataSource.getAllFolders(userId)
    }

    override suspend fun addFolder(name: String, userId: String) {
        val folderId = java.util.UUID.randomUUID().toString()
        localDataSource.addFolder(
            FolderEntity(
                folderId = folderId, title = name, userId = userId
            )
        )
        remoteDataSource.addFolder(
            folder = Folder(
                id = folderId, title = name, userId = userId
            )
        )
    }

    override suspend fun deleteFolder(folderId: String, userId: String) {
        localDataSource.deleteFolderById(folderId, userId)
        remoteDataSource.deleteFolderById(folderId)
    }

    override suspend fun updateFolderName(
        folderId: String, userId: String, newFolderName: String
    ) {
        localDataSource.updateFolderName(
            folderId = folderId, newFolderName = newFolderName, userId = userId
        )
        remoteDataSource.updateFolderName(folderId, newFolderName = newFolderName)
    }
}