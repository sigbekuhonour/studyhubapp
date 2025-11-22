package com.example.studyhubapp.data.datasource.local

import com.example.studyhubapp.domain.model.Flashcard
import com.example.studyhubapp.domain.model.Folder
import com.example.studyhubapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAllFolders(): Flow<List<Folder>>
    fun getAllNotes(): Flow<List<Note>>
    fun getAllFlashcards(): Flow<List<Flashcard>>
    suspend fun deleteFolderById(folderId: Int)
    suspend fun deleteFlashcardById(flashcardId: Int, noteId: Int)
    suspend fun deleteNoteById(folderId: Int, noteId: Int)
    suspend fun addFolder(folder: Folder)
    suspend fun addFlashcard(flashcard: Flashcard)
    suspend fun addNote(note: Note)
    suspend fun updateFlashcardContent(newContent: String, id: Int)
    suspend fun saveNoteChanges(
        folderId: Int,
        noteId: Int,
        title: String? = null,
        content: String? = null
    )

    suspend fun updateFolderName(folderId: Int, newFolderName: String)
}