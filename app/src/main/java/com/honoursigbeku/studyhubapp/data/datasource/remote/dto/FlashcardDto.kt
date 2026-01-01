package com.honoursigbeku.studyhubapp.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class FlashcardDto(val id: String, val ownerNoteId: String, val content: String)