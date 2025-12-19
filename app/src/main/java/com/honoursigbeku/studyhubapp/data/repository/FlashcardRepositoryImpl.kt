package com.honoursigbeku.studyhubapp.data.repository

import com.honoursigbeku.studyhubapp.data.datasource.local.LocalDataSource
import com.honoursigbeku.studyhubapp.data.datasource.remote.RemoteDataSource
import com.honoursigbeku.studyhubapp.domain.model.Flashcard
import com.honoursigbeku.studyhubapp.domain.repository.FlashcardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class FlashcardRepositoryImpl(
    private val localDataSourceImpl: LocalDataSource,
    private val remoteDataSourceImpl: RemoteDataSource
) : FlashcardRepository {
    override fun getAllFlashcards(): Flow<List<Flashcard>> = localDataSourceImpl.getAllFlashcards()


    override suspend fun addFlashcard(ownerNoteId: Int, content: String) {
        val flashcardId = localDataSourceImpl.getAllFlashcards().first().size
        localDataSourceImpl.addFlashcard(
            Flashcard(
                id = flashcardId, ownerNoteId = ownerNoteId, content = content
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
