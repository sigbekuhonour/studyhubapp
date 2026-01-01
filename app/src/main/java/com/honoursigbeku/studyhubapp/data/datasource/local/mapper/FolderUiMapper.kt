package com.honoursigbeku.studyhubapp.data.datasource.local.mapper

import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FolderEntity
import com.honoursigbeku.studyhubapp.domain.model.Folder

fun FolderEntity.toDomainModel() = Folder(
    id = folderId, title = title,
    userId = userId
)