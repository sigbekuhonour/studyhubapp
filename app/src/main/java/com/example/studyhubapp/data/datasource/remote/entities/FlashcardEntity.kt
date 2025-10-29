package com.example.studyhubapp.data.datasource.remote.entities

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
    @PrimaryKey(autoGenerate = true) val flashcardId: Int = 0,
    val ownerNoteId: Int,
    val content: String
)