package com.example.studyhubapp.data.datasource.remote.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "folders"
)
data class Folder(
    @PrimaryKey val folderId: Int = 0,
    val ownerUserId: String,
    val title: String?
)
