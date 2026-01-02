package com.honoursigbeku.studyhubapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FolderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Query("SELECT * FROM folders WHERE userId = :userId")
    fun getAllFolders(userId: String): Flow<List<FolderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFolder(folder: FolderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFolders(folders: List<FolderEntity>)

    @Query("SELECT COUNT(*) FROM folders WHERE userId = :userId")
    suspend fun getFolderCount(userId: String): Int

    @Query("DELETE FROM folders WHERE folderId = :id AND userId = :userId AND title NOT IN ('Quick Notes','Shared Notes','Deleted Notes')")
    suspend fun deleteFolderById(id: String, userId: String)

    @Query("UPDATE folders SET title = :newFolderName WHERE folderId = :folderId AND userId = :userId AND title NOT IN ('Quick Notes','Shared Notes','Deleted Notes')")
    suspend fun updateFolderName(newFolderName: String, userId: String, folderId: String)
}
