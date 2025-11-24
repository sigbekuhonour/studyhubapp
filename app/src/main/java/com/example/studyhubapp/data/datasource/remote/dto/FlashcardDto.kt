package com.example.studyhubapp.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class FlashcardDto(val id: Int, val ownerNoteId: Int, val content: String)