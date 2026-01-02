package com.honoursigbeku.studyhubapp.data.datasource.local

import android.util.Log
import com.honoursigbeku.studyhubapp.data.datasource.local.dao.FlashcardDao
import com.honoursigbeku.studyhubapp.data.datasource.local.dao.FolderDao
import com.honoursigbeku.studyhubapp.data.datasource.local.dao.NoteDao
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FlashcardEntity
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FolderEntity
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.NoteEntity
import com.honoursigbeku.studyhubapp.data.datasource.local.mapper.toDomainModel
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
    override fun getAllFolders(userId: String): Flow<List<Folder>> =
        folderDao.getAllFolders(userId).map {
            it.map(FolderEntity::toDomainModel)
        }


    override fun getAllNotes(): Flow<List<Note>> =
        noteDao.getAllNotes().map { it.map(NoteEntity::toDomainModel) }

    override fun getAllFlashcards(): Flow<List<Flashcard>> =
        flashcardDao.getAllFlashcards().map { it.map(FlashcardEntity::toDomainModel) }

    override suspend fun getFolderCount(userId: String): Int =
        folderDao.getFolderCount(userId)


    override suspend fun deleteFolderById(folderId: String, userId: String) {
        folderDao.deleteFolderById(folderId, userId)
    }

    override suspend fun deleteFlashcardById(flashcardId: String, noteId: String) {
        flashcardDao.deleteFlashcardById(flashcardId, noteId)
    }

    override suspend fun deleteNoteById(folderId: String, noteId: String) {
        noteDao.deleteNoteById(folderId, noteId)
    }

    override suspend fun addFolder(folder: FolderEntity) {
        folderDao.addFolder(folder)
        Log.i("LocalStorageDataSourceImpl", "we did get here")
        val count = folderDao.getFolderCount(userId = folder.userId)
        Log.d("ROOM_SAVE", "Total folders for this user in Room: $count")
    }

    override suspend fun addFlashcard(flashcard: FlashcardEntity) {
        flashcardDao.addFlashcard(flashcard)
    }

    override suspend fun addNote(note: NoteEntity) {
        noteDao.addNote(note)
    }

    override suspend fun updateFlashcardContent(newContent: String, id: String) {
        flashcardDao.updateFlashcardContent(newContent, id)
    }

    override suspend fun saveNoteChanges(
        folderId: String,
        noteId: String,
        title: String?,
        content: String?
    ) {
        noteDao.saveNoteChanges(folderId, noteId, title, content)
    }

    override suspend fun updateFolderName(folderId: String, userId: String, newFolderName: String) {
        folderDao.updateFolderName(
            newFolderName = newFolderName,
            folderId = folderId,
            userId = userId
        )
    }

    override suspend fun insertAllFolders(entities: List<FolderEntity>) {
        folderDao.insertAllFolders(entities)
    }
}




