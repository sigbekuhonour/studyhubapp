package com.honoursigbeku.studyhubapp.data.datasource.local.mapper

import com.honoursigbeku.studyhubapp.data.datasource.local.entities.NoteEntity
import com.honoursigbeku.studyhubapp.domain.model.Note

fun NoteEntity.toDomainModel() =
    Note(id = noteId, title = title, content = content, folderId = ownerFolderId)


fun Note.toEntity() = NoteEntity(
    noteId = 0,
    title = title,
    content = content,
    ownerFolderId = folderId
)