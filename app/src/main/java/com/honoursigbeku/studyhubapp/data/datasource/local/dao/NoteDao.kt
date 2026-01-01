package com.honoursigbeku.studyhubapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(notes: NoteEntity)

    @Query("DELETE FROM notes WHERE noteId = :noteId AND ownerFolderId = :folderId")
    suspend fun deleteNoteById(folderId: String, noteId: String)

    @Query(
        """
        UPDATE notes
        SET
            title = COALESCE(:title, title),
            content = COALESCE(:content, content),
            ownerFolderId = COALESCE(:folderId, ownerFolderId)
        WHERE noteId = :noteId
    """
    )
    suspend fun saveNoteChanges(
        folderId: String,
        noteId: String,
        title: String?,
        content: String?
    )
}