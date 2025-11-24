package com.example.studyhubapp.data.datasource.remote.mapper

import com.example.studyhubapp.data.datasource.remote.dto.FolderDto
import com.example.studyhubapp.domain.model.Folder


fun Folder.toDto(): FolderDto {
    return FolderDto(
        id = this.id,
        ///fix this
        userId = "TBD",
        title = this.title,
    )
}

fun FolderDto.toDomain(): Folder {
    return Folder(
        id = this.id,
        title = this.title,
    )
}