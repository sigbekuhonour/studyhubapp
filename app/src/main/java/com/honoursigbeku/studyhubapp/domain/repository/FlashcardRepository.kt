package com.honoursigbeku.studyhubapp.domain.repository

import com.honoursigbeku.studyhubapp.domain.model.Flashcard
import kotlinx.coroutines.flow.Flow


interface FlashcardRepository {
    fun getAllFlashcards(): Flow<List<Flashcard>>
    suspend fun addFlashcard(ownerNoteId: Int, content: String)
    suspend fun deleteFlashcardByNoteId(flashcardId: Int, noteId: Int)
    suspend fun updateFlashcardContent(
        newContent: String, id: Int
    )
}