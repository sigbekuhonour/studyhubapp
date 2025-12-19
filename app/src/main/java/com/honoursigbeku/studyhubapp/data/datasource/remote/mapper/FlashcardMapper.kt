package com.honoursigbeku.studyhubapp.data.datasource.remote.mapper

import com.honoursigbeku.studyhubapp.data.datasource.remote.dto.FlashcardDto
import com.honoursigbeku.studyhubapp.domain.model.Flashcard

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