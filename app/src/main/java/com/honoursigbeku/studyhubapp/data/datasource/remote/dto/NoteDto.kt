package com.honoursigbeku.studyhubapp.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class NoteDto(
    val id: String,
    val ownerFolderId: String,
    val title: String,
    val content: String
)