package com.example.studyhubapp.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.studyhubapp.component.note.Note
import com.example.studyhubapp.component.notefolder.NoteFolder
import java.util.UUID


class AppViewModel : ViewModel() {
    //main functions include the following
    //it would be key to have a mutable list of folders
    private val _folders = mutableStateListOf<NoteFolder>()
    val folders: List<NoteFolder> get() = _folders

    //add folder
    fun addFolder(name: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//
//        }
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