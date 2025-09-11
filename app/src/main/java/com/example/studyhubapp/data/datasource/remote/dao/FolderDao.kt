package com.example.studyhubapp.data.datasource.remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.studyhubapp.data.datasource.remote.entities.FolderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Query("SELECT * FROM folders")
    fun getAllFolders(): Flow<List<FolderEntity>>

    @Insert
    suspend fun addFolder(folder: FolderEntity)

    @Query("DELETE FROM folders WHERE folderId = :id")
    suspend fun deleteFolderById(id: Int)

    @Query("UPDATE folders SET title = :newFolderName WHERE folderId = :folderId")
    suspend fun updateFolderName(newFolderName: String, folderId: Int)
}
