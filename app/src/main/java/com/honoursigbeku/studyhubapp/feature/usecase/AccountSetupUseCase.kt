package com.honoursigbeku.studyhubapp.feature.usecase

import com.honoursigbeku.studyhubapp.data.datasource.local.LocalDataSource
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FolderEntity
import com.honoursigbeku.studyhubapp.data.datasource.remote.RemoteDataSource
import com.honoursigbeku.studyhubapp.domain.model.Folder
import com.honoursigbeku.studyhubapp.feature.usecase.dto.AccountSetupResult
import kotlinx.coroutines.flow.first

interface AccountSetupUseCase {
    suspend fun execute(userId: String): AccountSetupResult
}

class AccountSetupUseCaseImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource//
) : AccountSetupUseCase {
    val defaultFolders = listOf("Quick Notes", "Shared Notes", "Deleted Notes")
    override suspend fun execute(userId: String): AccountSetupResult {
        return try {
            val localFolders = localDataSource.getAllFolders(userId).first()
            if (localFolders.isEmpty()) {
                val remoteFolders = remoteDataSource.getAllFolders(userId)
                if (remoteFolders.isEmpty()) {
                    defaultFolders.forEach { folder ->
                        val folderId = java.util.UUID.randomUUID().toString()
                        localDataSource.addFolder(
                            folder = FolderEntity(
                                folderId = folderId,
                                userId = userId,
                                title = folder
                            )
                        )
                        remoteDataSource.addFolder(
                            folder = Folder(
                                id = folderId,
                                title = folder,
                                userId = userId
                            )
                        )

                    }
                } else {
                    remoteFolders.forEach { remoteFolder ->
                        localDataSource.addFolder(
                            folder = FolderEntity(
                                folderId = remoteFolder.id,
                                userId = remoteFolder.userId,
                                title = remoteFolder.title
                            )
                        )
                    }
                }
            }
            AccountSetupResult.Success
        } catch (e: Exception) {
            AccountSetupResult.Failure
        }
    }
}