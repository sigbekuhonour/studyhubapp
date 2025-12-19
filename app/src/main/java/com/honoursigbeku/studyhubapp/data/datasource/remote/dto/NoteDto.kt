package com.honoursigbeku.studyhubapp.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class NoteDto(val id: Int, val ownerFolderId: Int, val title: String, val content: String)