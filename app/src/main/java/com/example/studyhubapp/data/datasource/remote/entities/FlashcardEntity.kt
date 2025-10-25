package com.example.studyhubapp.data.datasource.remote.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "flashcards"
)
data class FlashcardEntity(
    @PrimaryKey(autoGenerate = true) val flashcardId: Int = 0,
    val ownerNoteId: Int,
    val content: String
)