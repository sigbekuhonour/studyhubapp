package com.example.studyhubapp.data.datasource.remote.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "folders"
)
data class FolderEntity(
    @PrimaryKey(autoGenerate = true) val folderId: Int = 0,
    val title: String
)
