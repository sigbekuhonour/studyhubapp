package com.example.studyhubapp.data.datasource.remote

import com.example.studyhubapp.domain.model.Flashcard
import com.example.studyhubapp.domain.model.Folder
import com.example.studyhubapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

class RemoteStorageDataSourceImpl : RemoteDataSource {
    override fun getAllFolders(): Flow<List<Folder>> {
        TODO("Not yet implemented")
    }

    override fun getAllNotes(): Flow<List<Note>> {
        TODO("Not yet implemented")
    }

    override fun getAllFlashcards(): Flow<List<Flashcard>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFolderById(folderId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFlashcardById(flashcardId: Int, noteId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNoteById(folderId: Int, noteId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun addFolder(folder: Folder) {
        TODO("Not yet implemented")
    }

    override suspend fun addFlashcard(flashcard: Flashcard) {
        TODO("Not yet implemented")
    }

    override suspend fun addNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun updateFlashcardContent(newContent: String, id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun saveNoteChanges(
        folderId: Int,
        noteId: Int,
        title: String?,
        content: String?
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun updateFolderName(folderId: Int, newFolderName: String) {
        TODO("Not yet implemented")
    }


}
