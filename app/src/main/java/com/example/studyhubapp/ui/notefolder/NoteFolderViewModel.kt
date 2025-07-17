package com.example.studyhubapp.ui.notefolder

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.studyhubapp.R
import com.example.studyhubapp.ui.note.Note

class NoteFolderViewModel : ViewModel() {
    //main functions include the following
    //it would be key to have a mutable list of notes
    private val _folders = mutableStateListOf<NoteFolder>(
        NoteFolder(id = 0, icon = R.drawable.folder_icon, name = "Quick Notes"),
        NoteFolder(id = 1, icon = R.drawable.shared_folder, name = "Shared Notes"),
        NoteFolder(id = 2, icon = R.drawable.deleted_folder, name = "Recently Deleted")
    )

    private val _recentlyDeletedFolder = mutableStateListOf<NoteFolder>()
    val folders: List<NoteFolder> get() = _folders

    //add folder
    fun addFolder(name: String) {
        val folderId = _folders.size
        _folders.add(NoteFolder(id = folderId, name = name))
    }

    //delete folder
    fun deleteFolder(name: String) {
        //add to recently deleted first before deleting
        val folderId = _recentlyDeletedFolder.size
        val folderToAddNote = _folders.find { (it.name) == name }
        if (folderToAddNote != null) {
            _recentlyDeletedFolder.add(NoteFolder(id = folderId, name = folderToAddNote.name))
        }

        _folders.removeIf { it.name == name }
    }

    //add notes
    fun addNotesToFolderWithId(folderName: String, title: String, content: String?) {
        val folderToAddNote = _folders.find { (it.name) == folderName }
        val folderToAddNoteId = folderToAddNote?.id
        val noteId = (folderToAddNote?.listOfNotes?.size?.plus(1) ?: 0)
        folderToAddNote?.listOfNotes?.add(
            Note(
                folderId = folderToAddNoteId,
                title = title,
                content = content,
                id = noteId
            )
        )
    }

    //delete notes in notes
    fun deleteNotesInFolderWithId(folderName: String, noteId: Int) {
        val folderToDeleteNote = _folders.find { it.name == folderName }
        folderToDeleteNote?.listOfNotes?.removeIf { it.id == noteId }
    }
}