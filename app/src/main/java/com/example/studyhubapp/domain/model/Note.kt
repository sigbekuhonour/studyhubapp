package com.example.studyhubapp.domain.model

data class Note(val id: Int, val folderId: Int, val title: String, val content: String = "")