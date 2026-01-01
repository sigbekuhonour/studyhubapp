package com.honoursigbeku.studyhubapp.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class FolderDto(val id: String, val userId: String, val title: String)