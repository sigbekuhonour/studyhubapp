package com.honoursigbeku.studyhubapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FolderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Query("SELECT * FROM folders")
    fun getAllFolders(): Flow<List<FolderEntity>>

    @Insert
    suspend fun addFolder(folder: FolderEntity)

    @Query("DELETE FROM folders WHERE folderId = :id AND title NOT IN ('Quick Notes','Shared Notes','Deleted Notes')")
    suspend fun deleteFolderById(id: Int)

    @Query("UPDATE folders SET title = :newFolderName WHERE folderId = :folderId AND title NOT IN ('Quick Notes','Shared Notes','Deleted Notes')")
    suspend fun updateFolderName(newFolderName: String, folderId: Int)
}
