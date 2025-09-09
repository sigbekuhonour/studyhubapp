package com.example.studyhubapp.data.datasource

import com.example.studyhubapp.domain.model.Folder
import com.example.studyhubapp.domain.model.Note
import kotlinx.coroutines.flow.StateFlow

interface DataSource {
    fun getAllFolders(): StateFlow<List<Folder>>
    fun getAllNotes(): StateFlow<List<Note>>
    suspend fun deleteFolderById(folderId: Int)
    suspend fun deleteNoteById(folderId: Int, noteId: Int)
    suspend fun addFolder(folder: Folder)
    suspend fun addNote(note: Note)
    suspend fun saveNoteChanges(
        folderId: Int,
        noteId: Int,
        title: String? = null,
        content: String? = null
    )

    suspend fun updateFolderName(folderId: Int, newFolderName: String)
}