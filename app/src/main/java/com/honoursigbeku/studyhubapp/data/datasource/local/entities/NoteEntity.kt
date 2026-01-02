package com.honoursigbeku.studyhubapp.data.datasource.local.entities

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
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("ownerFolderId")]
)
data class NoteEntity(
    @PrimaryKey val noteId: String,
    val title: String,
    val content: String,
    val ownerFolderId: String
)