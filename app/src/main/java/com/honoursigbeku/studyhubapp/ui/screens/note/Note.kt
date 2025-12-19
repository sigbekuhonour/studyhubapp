package com.honoursigbeku.studyhubapp.ui.screens.note

data class Note(
    val id: Int,
    val folderId: Int,
    val title: String,
    val content: String? = null
)


