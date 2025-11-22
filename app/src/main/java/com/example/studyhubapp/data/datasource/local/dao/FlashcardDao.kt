package com.example.studyhubapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.studyhubapp.data.datasource.local.entities.FlashcardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM flashcards")
    fun getAllFlashcards(): Flow<List<FlashcardEntity>>

    @Insert
    suspend fun addFlashcard(folder: FlashcardEntity)

    @Query("DELETE FROM flashcards WHERE flashcardId = :id AND ownerNoteId = :ownerNoteId ")
    suspend fun deleteFlashcardById(id: Int, ownerNoteId: Int)

    @Query("UPDATE flashcards SET content = :newContent WHERE flashcardId = :id")
    suspend fun updateFlashcardContent(newContent: String, id: Int)
}