package com.example.studyhubapp.data.repository

import com.example.studyhubapp.data.datasource.DataSource
import com.example.studyhubapp.domain.model.Flashcard
import com.example.studyhubapp.domain.repository.FlashcardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class FlashcardRepositoryImpl(private val dataSourceImpl: DataSource) : FlashcardRepository {
    override fun getAllFlashcards(): Flow<List<Flashcard>> =
        dataSourceImpl.getAllFlashcards()


    override suspend fun addFlashcard(ownerNoteId: Int, content: String) {
        val flashcardId = dataSourceImpl.getAllFlashcards().first().size
        dataSourceImpl.addFlashcard(
            Flashcard(
                id = flashcardId,
                ownerNoteId = ownerNoteId,
                content = content
            )
        )
    }

    override suspend fun deleteFlashcardByNoteId(flashcardId: Int, noteId: Int) {
        dataSourceImpl.deleteFlashcardById(flashcardId, noteId)
    }

    override suspend fun updateFlashcardContent(newContent: String, id: Int) {
        dataSourceImpl.updateFlashcardContent(newContent, id)
    }
}
