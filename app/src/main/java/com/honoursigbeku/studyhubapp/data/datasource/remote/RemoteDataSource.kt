package com.honoursigbeku.studyhubapp.data.datasource.remote

import com.honoursigbeku.studyhubapp.domain.model.Flashcard
import com.honoursigbeku.studyhubapp.domain.model.Folder
import com.honoursigbeku.studyhubapp.domain.model.Note

interface RemoteDataSource {
    suspend fun getAllFolders(): List<Folder>
    suspend fun getAllNotes(): List<Note>
    suspend fun getAllFlashcards(): List<Flashcard>
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