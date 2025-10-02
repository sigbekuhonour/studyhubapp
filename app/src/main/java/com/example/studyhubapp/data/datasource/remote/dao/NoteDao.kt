package com.example.studyhubapp.data.datasource.remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.studyhubapp.data.datasource.remote.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Insert
    suspend fun addNote(notes: NoteEntity)

    @Query("DELETE FROM notes WHERE noteId = :noteId AND ownerFolderId = :folderId")
    suspend fun deleteNoteById(folderId: Int, noteId: Int)

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
        folderId: Int,
        noteId: Int,
        title: String?,
        content: String?
    )
}