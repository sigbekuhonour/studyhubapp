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
    suspend fun deleteNoteById(noteId: Int, folderId: Int)

    @Query("UPDATE notes SET title = :title,content = :content WHERE ownerFolderId = :folderId AND noteId = :noteId")
    suspend fun saveNoteChanges(
        folderId: Int,
        noteId: Int,
        title: String?,
        content: String?
    )
}