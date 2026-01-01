package com.honoursigbeku.studyhubapp.data.repository

import com.honoursigbeku.studyhubapp.data.datasource.local.LocalDataSource
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FlashcardEntity
import com.honoursigbeku.studyhubapp.data.datasource.remote.RemoteDataSource
import com.honoursigbeku.studyhubapp.domain.model.Flashcard
import com.honoursigbeku.studyhubapp.domain.repository.FlashcardRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class FlashcardRepositoryImpl(
    private val localDataSourceImpl: LocalDataSource,
    private val remoteDataSourceImpl: RemoteDataSource
) : FlashcardRepository {
    override fun getAllFlashcards(): Flow<List<Flashcard>> = localDataSourceImpl.getAllFlashcards()


    override suspend fun addFlashcard(ownerNoteId: String, content: String) {
        val flashcardId = UUID.randomUUID().toString()
        localDataSourceImpl.addFlashcard(
            FlashcardEntity(
                flashcardId = flashcardId, ownerNoteId = ownerNoteId, content = content
            )
        )
        remoteDataSourceImpl.addFlashcard(
            flashcard = Flashcard(
                id = flashcardId, ownerNoteId = ownerNoteId, content = content
            )
        )
    }

    override suspend fun deleteFlashcardByNoteId(flashcardId: String, noteId: String) {
        localDataSourceImpl.deleteFlashcardById(flashcardId, noteId)
        remoteDataSourceImpl.deleteFlashcardById(flashcardId = flashcardId, noteId = noteId)
    }

    override suspend fun syncFlashcardsFromRemote() {
        val remoteFlashcards = remoteDataSourceImpl.getAllFlashcards()
        if (remoteFlashcards.isNotEmpty()) {
            remoteFlashcards.forEach { flashcard ->
                localDataSourceImpl.addFlashcard(
                    flashcard = FlashcardEntity(
                        flashcardId = flashcard.id,
                        ownerNoteId = flashcard.ownerNoteId,
                        content = flashcard.content
                    )
                )
            }
        }

    }
}
