package com.honoursigbeku.studyhubapp.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "folders"
)
data class FolderEntity(
    @PrimaryKey val folderId: String,
    val userId: String,
    val title: String
)
