package com.example.studyhubapp.ui.notefolder

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studyhubapp.R
import com.example.studyhubapp.domain.NoteFolderRepository
import com.example.studyhubapp.domain.NoteFolderRepositoryImpl
import com.example.studyhubapp.domain.datasource.local.LocalStorageDataSourceImpl
import com.example.studyhubapp.domain.model.Folder
import kotlinx.coroutines.launch

class NoteFolderViewModel(
    private val folderRepository: NoteFolderRepository
) : ViewModel() {

    private var _folders = mutableStateListOf<NoteFolder>()

    val folders: List<NoteFolder> get() = _folders

    init {
        viewModelScope.launch {
            val fetchedFolders = folderRepository.getFolders() // Fetch data
            loadData(fetchedFolders) // Then call loadData with the fetched data
        }
    }

    private fun loadData(currentList: List<Folder>) {
        viewModelScope.launch {
            try {
                val defaultFolders = folderRepository.getFolders()
                defaultFolders.forEach { eachFolders ->
                    _folders.add(
                        NoteFolder(
                            id = eachFolders.id,
                            icon = getIcon(folder = eachFolders),
                            name = eachFolders.title
                        )
                    )
                }
            } catch (e: Exception) {
                println("Error loading data")
            }
        }
    }

    fun getFolderContentSize(folderId: Int): Int {
        var contentSize: Int = 0
        viewModelScope.launch {
            contentSize = folderRepository.getFolderContentSize(folderId)
        }
        return contentSize
    }

    private fun getIcon(folder: Folder): Int {
        var icon: Int = R.drawable.folder_icon
        if (folder.title == "Shared Notes") {
            icon = R.drawable.shared_folder
        } else if (folder.title == "Deleted Notes") {
            icon = R.drawable.deleted_folder
        }
        return icon
    }

    fun getFolderId(name: String): Int {
        return _folders.indexOfFirst { eachFolder -> eachFolder.name == name }
    }

    //add folder
    fun addFolder(name: String) {
        viewModelScope.launch {
            folderRepository.addFolder(name)
            val updated = folderRepository.getFolders()
            _folders.clear()
            updated.forEach { eachFolder ->
                _folders.add(
                    NoteFolder(
                        id = eachFolder.id,
                        icon = getIcon(eachFolder),
                        name = eachFolder.title
                    )
                )
            }
        }
    }

    //delete folder
    fun deleteFolder(folderId: Int) {
        //add to recently deleted first before deleting
        viewModelScope.launch {
            folderRepository.deleteFolder(folderId)
            val updated = folderRepository.getFolders()
            _folders.clear()
            updated.forEach { eachFolder ->
                _folders.add(
                    NoteFolder(
                        id = eachFolder.id,
                        icon = getIcon(eachFolder),
                        name = eachFolder.title
                    )
                )
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                NoteFolderViewModel(
                    folderRepository = NoteFolderRepositoryImpl(LocalStorageDataSourceImpl())
                )
            }
        }
    }
}