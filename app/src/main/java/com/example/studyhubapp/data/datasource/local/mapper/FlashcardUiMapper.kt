package com.example.studyhubapp.data.datasource.local.mapper

import com.example.studyhubapp.data.datasource.local.entities.FlashcardEntity
import com.example.studyhubapp.domain.model.Flashcard

fun FlashcardEntity.toDomainModel() = Flashcard(
    id = flashcardId, ownerNoteId = ownerNoteId, content = content
)

fun Flashcard.toEntity() = FlashcardEntity(
    flashcardId = 0, ownerNoteId = ownerNoteId, content = content
)