package com.example.studyhubapp.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class FolderDto(val id: Int, val userId: String, val title: String)