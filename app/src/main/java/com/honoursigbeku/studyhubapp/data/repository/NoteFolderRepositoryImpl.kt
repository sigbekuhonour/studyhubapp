package com.honoursigbeku.studyhubapp.data.repository

import com.honoursigbeku.studyhubapp.data.datasource.local.LocalDataSource
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FolderEntity
import com.honoursigbeku.studyhubapp.data.datasource.remote.RemoteDataSource
import com.honoursigbeku.studyhubapp.domain.model.Folder
import com.honoursigbeku.studyhubapp.domain.repository.NoteFolderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteFolderRepositoryImpl(
    private val localDataSourceImpl: LocalDataSource,
    private val remoteDataSourceImpl: RemoteDataSource
) : NoteFolderRepository {

    override fun getFolderContentSize(folderId: String): Flow<Int> {
        return localDataSourceImpl.getAllNotes()
            .map { notes -> notes.count { it.folderId == folderId } }
    }

    override fun getAllFolders(userId: String): Flow<List<Folder>> {
        return localDataSourceImpl.getAllFolders(userId)
    }

    override suspend fun addFolder(name: String, userId: String) {
        val folderId = java.util.UUID.randomUUID().toString()
        localDataSourceImpl.addFolder(
            FolderEntity(
                folderId = folderId, title = name, userId = userId
            )
        )
        remoteDataSourceImpl.addFolder(
            folder = Folder(
                id = folderId, title = name, userId = userId
            )
        )
    }

    override suspend fun deleteFolder(folderId: String, userId: String) {
        localDataSourceImpl.deleteFolderById(folderId, userId)
        remoteDataSourceImpl.deleteFolderById(folderId)
    }

    override suspend fun updateFolderName(
        folderId: String, userId: String, newFolderName: String
    ) {
        localDataSourceImpl.updateFolderName(
            folderId = folderId, newFolderName = newFolderName, userId = userId
        )
        remoteDataSourceImpl.updateFolderName(folderId, newFolderName = newFolderName)
    }

    override suspend fun syncFoldersFromRemote(userId: String) {
        val remoteFolders = remoteDataSourceImpl.getAllFolders(userId)
        val defaultFolders = listOf("Quick Notes", "Shared Notes", "Deleted Notes")
        if (remoteFolders.isNotEmpty()) {
            remoteFolders.forEach { folder ->
                localDataSourceImpl.addFolder(
                    FolderEntity(
                        folderId = folder.id,
                        userId = folder.userId,
                        title = folder.title
                    )
                )
            }
        } else {
            defaultFolders.forEach { folder ->
                this.addFolder(name = folder, userId = userId)
            }
        }

    }

}