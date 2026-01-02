package com.honoursigbeku.studyhubapp.data.datasource.remote

import com.honoursigbeku.studyhubapp.domain.model.Flashcard
import com.honoursigbeku.studyhubapp.domain.model.Folder
import com.honoursigbeku.studyhubapp.domain.model.Note
import com.honoursigbeku.studyhubapp.domain.model.User

interface RemoteDataSource {
    suspend fun addNewUser(user: User)
    suspend fun getAllFolders(userId: String): List<Folder>
    suspend fun getFoldersCount(userId: String): Int
    suspend fun getAllNotes(): List<Note>
    suspend fun getAllFlashcards(): List<Flashcard>
    suspend fun deleteFolderById(folderId: String)
    suspend fun deleteFlashcardById(flashcardId: String, noteId: String)
    suspend fun deleteNoteById(folderId: String, noteId: String)
    suspend fun addFolder(folder: Folder)
    suspend fun addFlashcard(flashcard: Flashcard)
    suspend fun addNote(note: Note)
    suspend fun updateFlashcardContent(newContent: String, id: String)
    suspend fun saveNoteChanges(
        folderId: String,
        noteId: String,
        title: String? = null,
        content: String? = null
    )

    suspend fun updateFolderName(folderId: String, newFolderName: String)
}