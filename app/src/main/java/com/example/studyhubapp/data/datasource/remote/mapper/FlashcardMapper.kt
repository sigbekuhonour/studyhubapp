package com.example.studyhubapp.data.datasource.remote.mapper

import com.example.studyhubapp.data.datasource.remote.dto.FlashcardDto
import com.example.studyhubapp.domain.model.Flashcard

fun Flashcard.toDto(): FlashcardDto {
    return FlashcardDto(
        id = this.id,
        ownerNoteId = this.ownerNoteId,
        content = this.content,
    )
}

fun FlashcardDto.toDomain(): Flashcard {
    return Flashcard(
        id = this.id,
        ownerNoteId = this.ownerNoteId,
        content = this.content,
    )
}