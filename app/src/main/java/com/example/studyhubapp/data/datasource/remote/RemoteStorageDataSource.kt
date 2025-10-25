package com.example.studyhubapp.data.datasource.remote

import com.example.studyhubapp.data.datasource.DataSource
import com.example.studyhubapp.data.datasource.remote.dao.FlashcardDao
import com.example.studyhubapp.data.datasource.remote.dao.FolderDao
import com.example.studyhubapp.data.datasource.remote.dao.NoteDao
import com.example.studyhubapp.data.datasource.remote.entities.FlashcardEntity
import com.example.studyhubapp.data.datasource.remote.entities.FolderEntity
import com.example.studyhubapp.data.datasource.remote.entities.NoteEntity
import com.example.studyhubapp.domain.model.Flashcard
import com.example.studyhubapp.domain.model.Folder
import com.example.studyhubapp.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemoteStorageDataSourceImpl(
    val folderDao: FolderDao,
    val noteDao: NoteDao,
    val flashcardDao: FlashcardDao
) : DataSource {

    override fun getAllFolders(): Flow<List<Folder>> =
        folderDao.getAllFolders().map {
            it.map(FolderEntity::toDomain)
        }


    override fun getAllNotes(): Flow<List<Note>> =
        noteDao.getAllNotes().map { it.map(NoteEntity::toDomain) }

    override fun getAllFlashcards(): Flow<List<Flashcard>> =
        flashcardDao.getAllFlashcards().map { it.map(FlashcardEntity::toDomain) }


    override suspend fun deleteFolderById(folderId: Int) {
        folderDao.deleteFolderById(folderId)
    }

    override suspend fun deleteFlashcardById(flashcardId: Int, noteId: Int) {
        flashcardDao.deleteFlashcardById(flashcardId, noteId)
    }

    override suspend fun deleteNoteById(folderId: Int, noteId: Int) {
        noteDao.deleteNoteById(folderId, noteId)
    }

    override suspend fun addFolder(folder: Folder) {
        folderDao.addFolder(folder.toEntity())
    }

    override suspend fun addFlashcard(flashcard: Flashcard) {
        flashcardDao.addFlashcard(flashcard.toEntity())
    }

    override suspend fun addNote(note: Note) {
        noteDao.addNote(note.toEntity())
    }

    override suspend fun updateFlashcardContent(newContent: String, id: Int) {
        flashcardDao.updateFlashcardContent(newContent, id)
    }

    override suspend fun saveNoteChanges(
        folderId: Int,
        noteId: Int,
        title: String?,
        content: String?
    ) {
        noteDao.saveNoteChanges(folderId, noteId, title, content)
    }

    override suspend fun updateFolderName(folderId: Int, newFolderName: String) {
        folderDao.updateFolderName(newFolderName, folderId)
    }

}

private fun FolderEntity.toDomain() = Folder(id = folderId, title = title)
private fun NoteEntity.toDomain() =
    Note(id = noteId, title = title, content = content, folderId = ownerFolderId)

private fun FlashcardEntity.toDomain() = Flashcard(
    id = flashcardId, ownerNoteId = ownerNoteId, content = content
)

private fun Folder.toEntity() = FolderEntity(folderId = 0, title = title)

private fun Note.toEntity() = NoteEntity(
    noteId = 0,
    title = title,
    content = content,
    ownerFolderId = folderId
)

private fun Flashcard.toEntity() = FlashcardEntity(
    flashcardId = 0, ownerNoteId = ownerNoteId, content = content
)
