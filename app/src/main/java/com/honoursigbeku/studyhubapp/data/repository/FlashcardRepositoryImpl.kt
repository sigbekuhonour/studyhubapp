package com.honoursigbeku.studyhubapp.data.repository

import com.honoursigbeku.studyhubapp.data.datasource.local.LocalDataSource
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FlashcardEntity
import com.honoursigbeku.studyhubapp.data.datasource.remote.RemoteDataSource
import com.honoursigbeku.studyhubapp.domain.model.Flashcard
import com.honoursigbeku.studyhubapp.domain.repository.FlashcardRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class FlashcardRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : FlashcardRepository {
    override fun getAllFlashcards(): Flow<List<Flashcard>> = localDataSource.getAllFlashcards()


    override suspend fun addFlashcard(ownerNoteId: String, content: String) {
        val flashcardId = UUID.randomUUID().toString()
        localDataSource.addFlashcard(
            FlashcardEntity(
                flashcardId = flashcardId, ownerNoteId = ownerNoteId, content = content
            )
        )
        remoteDataSource.addFlashcard(
            flashcard = Flashcard(
                id = flashcardId, ownerNoteId = ownerNoteId, content = content
            )
        )
    }

    override suspend fun deleteFlashcardByNoteId(flashcardId: String, noteId: String) {
        localDataSource.deleteFlashcardById(flashcardId, noteId)
        remoteDataSource.deleteFlashcardById(flashcardId = flashcardId, noteId = noteId)
    }

}
