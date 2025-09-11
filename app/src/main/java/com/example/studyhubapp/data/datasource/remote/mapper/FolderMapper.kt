package com.example.studyhubapp.data.datasource.remote.mapper

import com.example.studyhubapp.data.datasource.remote.entities.FolderEntity
import com.example.studyhubapp.domain.model.Folder


fun Folder.toEntity(): FolderEntity {
    return FolderEntity(
        folderId = this.id,
        title = this.title
    )
}