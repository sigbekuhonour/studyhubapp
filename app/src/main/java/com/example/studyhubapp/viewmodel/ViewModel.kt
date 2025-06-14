package com.example.studyhubapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import java.util.UUID


data class NoteFolder(
    val id: Int,
    val name: String,
    val listOfNotes: SnapshotStateList<Note> = mutableStateListOf()
)

data class Note(
    val id: Int,
    val folderId: Int?,
    val title: String,
    val content: String?
)

class AppViewModel : ViewModel() {
    //main functions include the following
    //it would be key to have a mutable list of folders
    private val _folders = mutableStateListOf<NoteFolder>()
    val folders: List<NoteFolder> get() = _folders

    //add folder
    fun addFolder(name: String) {
        val folderId = UUID.randomUUID().hashCode()
        _folders.add(NoteFolder(id = folderId, name = name))
    }

    //delete folder
    fun deleteFolder(name: String) {
        _folders.removeIf { it.name == name }
    }

    //add notes
    fun addNotesToFolderWithId(folderName: String, title: String, content: String?) {
        val folderToAddNote = _folders.find { (it.name) == folderName }
        val folderToAddNoteId = folderToAddNote?.id
        val noteId = (folderToAddNote?.listOfNotes?.size?.plus(1) ?: 0)
        folderToAddNote?.listOfNotes?.add(
            Note(
                id = noteId,
                folderId = folderToAddNoteId,
                title = title,
                content = content
            )
        )
    }

    //delete notes in folders
    fun deleteNotesInFolderWithId(folderName: String, noteId: Int) {
        val folderToDeleteNote = _folders.find { it.name == folderName }
        folderToDeleteNote?.listOfNotes?.removeIf { it.id == noteId }
    }
}