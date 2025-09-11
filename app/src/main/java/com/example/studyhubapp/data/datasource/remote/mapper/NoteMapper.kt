package com.example.studyhubapp.data.datasource.remote.mapper

import com.example.studyhubapp.data.datasource.remote.entities.NoteEntity
import com.example.studyhubapp.domain.model.Note

fun NoteEntity.toDomain(): Note {
    return Note(
        id = this.noteId,
        folderId = this.ownerFolderId,
        title = this.title,
        content = this.content
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        noteId = this.id,
        ownerFolderId = this.folderId,
        title = this.title,
        content = this.content
    )
}