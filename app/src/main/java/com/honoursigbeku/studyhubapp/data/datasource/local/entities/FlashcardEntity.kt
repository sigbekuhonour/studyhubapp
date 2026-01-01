package com.honoursigbeku.studyhubapp.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "flashcards",
    foreignKeys = [
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = ["noteId"],
            childColumns = ["ownerNoteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("ownerNoteId")]
)
data class FlashcardEntity(
    @PrimaryKey val flashcardId: String = java.util.UUID.randomUUID().toString(),
    val ownerNoteId: String,
    val content: String
)