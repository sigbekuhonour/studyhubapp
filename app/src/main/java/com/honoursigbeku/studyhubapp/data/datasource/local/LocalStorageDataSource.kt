package com.honoursigbeku.studyhubapp.data.datasource.local

import com.honoursigbeku.studyhubapp.data.datasource.local.dao.FlashcardDao
import com.honoursigbeku.studyhubapp.data.datasource.local.dao.FolderDao
import com.honoursigbeku.studyhubapp.data.datasource.local.dao.NoteDao
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FlashcardEntity
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FolderEntity
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.NoteEntity
import com.honoursigbeku.studyhubapp.data.datasource.local.mapper.toDomainModel
import com.honoursigbeku.studyhubapp.data.datasource.local.mapper.toEntity
import com.honoursigbeku.studyhubapp.domain.model.Flashcard
import com.honoursigbeku.studyhubapp.domain.model.Folder
import com.honoursigbeku.studyhubapp.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class LocalStorageDataSourceImpl(
    val folderDao: FolderDao,
    val noteDao: NoteDao,
    val flashcardDao: FlashcardDao
) : LocalDataSource {
    override fun getAllFolders(): Flow<List<Folder>> =
        folderDao.getAllFolders().map {
            it.map(FolderEntity::toDomainModel)
        }


    override fun getAllNotes(): Flow<List<Note>> =
        noteDao.getAllNotes().map { it.map(NoteEntity::toDomainModel) }

    override fun getAllFlashcards(): Flow<List<Flashcard>> =
        flashcardDao.getAllFlashcards().map { it.map(FlashcardEntity::toDomainModel) }


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




