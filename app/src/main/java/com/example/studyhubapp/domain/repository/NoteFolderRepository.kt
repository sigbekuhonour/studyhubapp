package com.example.studyhubapp.domain.repository

import com.example.studyhubapp.domain.model.Folder
import kotlinx.coroutines.flow.Flow

interface NoteFolderRepository {
    
    fun getAllFolders(): Flow<List<Folder>>
    fun getFolderContentSize(folderId: Int): Flow<Int>
    suspend fun addFolder(name: String)
    suspend fun deleteFolder(folderId: Int)
    suspend fun updateFolderName(folderId: Int, newFolderName: String)
}

