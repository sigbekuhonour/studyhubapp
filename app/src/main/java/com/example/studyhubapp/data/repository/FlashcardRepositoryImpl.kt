package com.example.studyhubapp.data.repository

import com.example.studyhubapp.data.datasource.local.LocalDataSource
import com.example.studyhubapp.data.datasource.remote.RemoteDataSource
import com.example.studyhubapp.domain.model.Flashcard
import com.example.studyhubapp.domain.repository.FlashcardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class FlashcardRepositoryImpl(
    private val localDataSourceImpl: LocalDataSource,
    private val remoteDataSourceImpl: RemoteDataSource
) : FlashcardRepository {
    override fun getAllFlashcards(): Flow<List<Flashcard>> =
        localDataSourceImpl.getAllFlashcards()


    override suspend fun addFlashcard(ownerNoteId: Int, content: String) {
        val flashcardId = localDataSourceImpl.getAllFlashcards().first().size
        localDataSourceImpl.addFlashcard(
            Flashcard(
                id = flashcardId,
                ownerNoteId = ownerNoteId,
                content = content
            )
        )
    }

    override suspend fun deleteFlashcardByNoteId(flashcardId: Int, noteId: Int) {
        localDataSourceImpl.deleteFlashcardById(flashcardId, noteId)
    }

    override suspend fun updateFlashcardContent(newContent: String, id: Int) {
        localDataSourceImpl.updateFlashcardContent(newContent, id)
    }
}
