package com.example.studyhubapp.data.datasource.local

import com.example.studyhubapp.data.datasource.DataSource
import com.example.studyhubapp.domain.model.Flashcard
import com.example.studyhubapp.domain.model.Folder
import com.example.studyhubapp.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


class LocalStorageDataSourceImpl : DataSource {
    private val _folders = MutableStateFlow<List<Folder>>(
        listOf(
            Folder(id = 0, title = "Quick Notes"),
            Folder(id = 1, title = "Shared Notes"),
            Folder(id = 2, title = "Deleted Notes")
        )
    )
    private val _notes = MutableStateFlow<List<Note>>(
        listOf(
            Note(id = 0, folderId = 0, title = "First Note", content = "First content"),
            Note(id = 1, folderId = 0, title = "Second Note", content = "Second content etc"),
            Note(id = 2, folderId = 1, title = "Third Note", content = "Third content etc"),
            Note(id = 3, folderId = 2, title = "Fourth Note", content = "Fourth content etc")
        )
    )

    private val _flashcards = MutableStateFlow<List<Flashcard>>(
        listOf(
            Flashcard(id = 0, ownerNoteId = 0, content = "My first flashcard"),
            Flashcard(id = 1, ownerNoteId = 0, content = "My second flashcard")
        )
    )


    override fun getAllFolders(): Flow<List<Folder>> = _folders
    override fun getAllNotes(): Flow<List<Note>> = _notes
    override fun getAllFlashcards(): Flow<List<Flashcard>> = _flashcards

    override suspend fun deleteFolderById(folderId: Int) {
        if (folderId in setOf(0, 1, 2)) return
        _folders.update { list -> list.filterNot { it.id == folderId } }
    }

    override suspend fun deleteFlashcardById(flashcardId: Int, noteId: Int) {
        _flashcards.update { list -> list.filterNot { it.id == flashcardId && it.ownerNoteId == noteId } }
    }

    override suspend fun deleteNoteById(folderId: Int, noteId: Int) {
        _notes.update { list -> list.filterNot { it.id == noteId } }
    }

    override suspend fun addFolder(folder: Folder) {
        _folders.value = _folders.value + folder
    }

    override suspend fun addFlashcard(flashcard: Flashcard) {
        _flashcards.value = _flashcards.value + flashcard
    }

    override suspend fun addNote(note: Note) {
        _notes.value = _notes.value + note
    }

    override suspend fun updateFlashcardContent(newContent: String, id: Int) {
        _flashcards.value = _flashcards.value.map { eachFlashcard ->
            if (eachFlashcard.id == id) {
                eachFlashcard.copy(content = newContent)
            } else {
                eachFlashcard
            }
        }
    }

    override suspend fun saveNoteChanges(
        folderId: Int,
        noteId: Int,
        title: String?,
        content: String?
    ) {
        _notes.value = _notes.value.map { eachNote ->
            if (eachNote.folderId == folderId && eachNote.id == noteId) {
                eachNote.copy(
                    title = title ?: eachNote.title,
                    content = content ?: eachNote.content
                )
            } else eachNote
        }
    }

    override suspend fun updateFolderName(folderId: Int, newFolderName: String) {
        _folders.value = _folders.value.map { eachFolder ->
            if (eachFolder.id == folderId) {
                eachFolder.copy(title = newFolderName)
            } else {
                eachFolder
            }
        }
    }
}