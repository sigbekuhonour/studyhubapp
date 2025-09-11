package com.example.studyhubapp.data.datasource.remote.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "notes"
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val noteId: Int = 0,
    val ownerFolderId: Int,
    val title: String,
    val content: String

)