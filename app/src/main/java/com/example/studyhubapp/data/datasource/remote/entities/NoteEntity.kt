package com.example.studyhubapp.data.datasource.remote.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "notes",
    foreignKeys = [
        ForeignKey(
            entity = FolderEntity::class,
            parentColumns = ["folderId"],
            childColumns = ["ownerFolderId"],
            onDelete = ForeignKey.CASCADE // or NO_ACTION if you’ll recycle manually
        )
    ],
    indices = [Index("ownerFolderId")]
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val noteId: Int = 0,
    val title: String,
    val content: String,
    val ownerFolderId: Int
)