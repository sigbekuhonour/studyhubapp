package com.example.studyhubapp.domain.model

data class Note(val id: Int, val folderId: Int, var title: String, var content: String = "")