package com.example.studyhubapp.domain

import com.example.studyhubapp.domain.model.Folder


interface NoteFolderRepository {

    suspend fun getFolder(id: Int): Folder
    suspend fun getFolders(): List<Folder>
    suspend fun addFolder(name: String)
    suspend fun deleteFolder(name: String)
}


class NoteFolderRepositoryImpl : NoteFolderRepository {
    private val _folders = mutableListOf<Folder>(
        Folder(id = 0, title = "Quick Notes"),
        Folder(id = 1, title = "Shared Notes"), Folder(id = 2, title = "Deleted Notes")
    )

    override suspend fun getFolder(id: Int): Folder {
        return _folders[id]
    }

    override suspend fun getFolders(): List<Folder> {
        return _folders
    }

    override suspend fun addFolder(name: String) {
        val folderId = _folders.size
        _folders.add(Folder(id = folderId, title = name))
    }

    override suspend fun deleteFolder(name: String) {
        _folders.removeAll { folder ->
            folder.title == name
        }
    }

}
