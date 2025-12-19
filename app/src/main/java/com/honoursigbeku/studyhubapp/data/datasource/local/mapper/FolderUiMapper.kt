package com.honoursigbeku.studyhubapp.data.datasource.local.mapper

import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FolderEntity
import com.honoursigbeku.studyhubapp.domain.model.Folder

fun Folder.toEntity() = FolderEntity(folderId = 0, title = title)
fun FolderEntity.toDomainModel() = Folder(id = folderId, title = title)