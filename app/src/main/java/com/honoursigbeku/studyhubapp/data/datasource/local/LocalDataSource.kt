package com.honoursigbeku.studyhubapp.data.datasource.local

import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FlashcardEntity
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FolderEntity
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.NoteEntity
import com.honoursigbeku.studyhubapp.domain.model.Flashcard
import com.honoursigbeku.studyhubapp.domain.model.Folder
import com.honoursigbeku.studyhubapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAllFolders(userId: String): Flow<List<Folder>>
    fun getAllNotes(): Flow<List<Note>>
    fun getAllFlashcards(): Flow<List<Flashcard>>
    suspend fun getFolderCount(userId: String): Int
    suspend fun deleteFolderById(folderId: String, userId: String)
    suspend fun deleteFlashcardById(flashcardId: String, noteId: String)
    suspend fun deleteNoteById(folderId: String, noteId: String)
    suspend fun addFolder(folder: FolderEntity)
    suspend fun insertAllFolders(entities: List<FolderEntity>)
    suspend fun addFlashcard(flashcard: FlashcardEntity)
    suspend fun addNote(note: NoteEntity)
    suspend fun updateFlashcardContent(newContent: String, id: String)
    suspend fun saveNoteChanges(
        folderId: String,
        noteId: String,
        title: String? = null,
        content: String? = null
    )

    suspend fun updateFolderName(folderId: String, userId: String, newFolderName: String)
    fun insertAllNotes(noteEntities: List<NoteEntity>)
    fun insertAllFlashcards(flashcardEntities: List<FlashcardEntity>)
}