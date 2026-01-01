package com.honoursigbeku.studyhubapp.data.datasource.remote.mapper

import com.honoursigbeku.studyhubapp.data.datasource.remote.dto.FolderDto
import com.honoursigbeku.studyhubapp.domain.model.Folder


fun Folder.toDto(): FolderDto {
    return FolderDto(
        id = this.id,
        userId = this.userId,
        title = this.title,
    )
}

fun FolderDto.toDomain(): Folder {
    return Folder(
        id = this.id,
        title = this.title,
        userId = this.userId,
    )
}