package com.honoursigbeku.studyhubapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FlashcardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM flashcards")
    fun getAllFlashcards(): Flow<List<FlashcardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFlashcard(folder: FlashcardEntity)

    @Query("DELETE FROM flashcards WHERE flashcardId = :id AND ownerNoteId = :ownerNoteId ")
    suspend fun deleteFlashcardById(id: String, ownerNoteId: String)

    @Query("UPDATE flashcards SET content = :newContent WHERE flashcardId = :id")
    suspend fun updateFlashcardContent(newContent: String, id: String)
}