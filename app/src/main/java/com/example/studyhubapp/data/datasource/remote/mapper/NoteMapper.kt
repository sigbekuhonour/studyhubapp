package com.example.studyhubapp.data.datasource.remote.mapper

import com.example.studyhubapp.data.datasource.remote.dto.NoteDto
import com.example.studyhubapp.domain.model.Note

fun Note.toDto(): NoteDto {
    return NoteDto(
        id = this.id,
        title = this.title,
        ownerFolderId = this.folderId,
        content = this.content
    )
}

fun NoteDto.toDomain(): Note {
    return Note(
        id = this.id,
        folderId = this.ownerFolderId,
        title = this.title,
        content = this.content,
    )
}