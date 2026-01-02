package com.honoursigbeku.studyhubapp.domain.repository

import com.honoursigbeku.studyhubapp.domain.model.Folder
import kotlinx.coroutines.flow.Flow

interface NoteFolderRepository {

    fun getAllFolders(userId: String): Flow<List<Folder>>
    fun getFolderContentSize(folderId: String): Flow<Int>
    suspend fun addFolder(name: String, userId: String)
    suspend fun deleteFolder(folderId: String, userId: String)
    suspend fun updateFolderName(folderId: String, userId: String, newFolderName: String)
}

