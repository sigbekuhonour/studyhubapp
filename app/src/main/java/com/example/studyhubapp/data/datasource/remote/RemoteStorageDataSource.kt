package com.example.studyhubapp.data.datasource.remote

import com.example.studyhubapp.data.datasource.DataSource
import com.example.studyhubapp.data.datasource.remote.dao.FolderDao
import com.example.studyhubapp.data.datasource.remote.dao.NoteDao
import com.example.studyhubapp.data.datasource.remote.entities.FolderEntity
import com.example.studyhubapp.data.datasource.remote.entities.NoteEntity
import com.example.studyhubapp.domain.model.Folder
import com.example.studyhubapp.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemoteStorageDataSourceImpl(val folderDao: FolderDao, val noteDao: NoteDao) : DataSource {

    override fun getAllFolders(): Flow<List<Folder>> {
        return folderDao.getAllFolders().map { folderList ->
            folderList.map { eachFolder ->
                Folder(id = eachFolder.folderId, title = eachFolder.title)
            }
        }
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { noteList ->
            noteList.map { eachNote ->
                Note(
                    id = eachNote.noteId,
                    title = eachNote.title,
                    folderId = eachNote.ownerFolderId,
                    content = eachNote.content
                )
            }
        }
    }

    override suspend fun deleteFolderById(folderId: Int) {
        folderDao.deleteFolderById(folderId)
    }

    override suspend fun deleteNoteById(folderId: Int, noteId: Int) {
        noteDao.deleteNoteById(folderId, noteId)
    }

    override suspend fun addFolder(folder: Folder) {
        folderDao.addFolder(folder.toEntity())
    }

    override suspend fun addNote(note: Note) {
        noteDao.addNote(note.toEntity())
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

private fun Folder.toEntity() = FolderEntity(
    folderId = this.id ?: 0,
    title = this.title,
)

private fun Note.toEntity() = NoteEntity(
    noteId = this.id,
    title = this.title,
    content = this.content,
    ownerFolderId = this.folderId
)
