package com.example.studyhubapp.ui.screens.notefolder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studyhubapp.R
import com.example.studyhubapp.data.datasource.local.LocalStorageDataSourceProvider
import com.example.studyhubapp.data.repository.NoteFolderRepositoryImpl
import com.example.studyhubapp.domain.repository.NoteFolderRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteFolderViewModel(
    private val folderRepository: NoteFolderRepository
) : ViewModel() {

    private var _folders = folderRepository.getFolders()

    val folders: StateFlow<List<Folder>>
        get() = _folders.map { folderList ->
            folderList.map { eachFolder ->
                Folder(id = eachFolder.id, icon = getIcon(eachFolder), name = eachFolder.title)
            }
        }.stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, emptyList())

    fun getFolderContentSize(folderId: Int): StateFlow<Int> {
        return folderRepository.getFolderContentSize(folderId)
            .stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = 0)
    }

    private fun getIcon(folder: com.example.studyhubapp.domain.model.Folder): Int {
        var icon: Int = R.drawable.folder_icon
        if (folder.title == "Shared Notes") {
            icon = R.drawable.shared_folder
        } else if (folder.title == "Deleted Notes") {
            icon = R.drawable.deleted_folder
        }
        return icon
    }

    fun getFolderId(name: String): Int {
        return _folders.value.indexOfFirst { eachFolder -> eachFolder.title == name }
    }

    //add folder
    fun addFolder(name: String) {
        viewModelScope.launch {
            folderRepository.addFolder(name)
        }
    }

    fun updateFolderName(folderId: Int, currentFolderName: String) {
        viewModelScope.launch {
            folderRepository.updateFolderName(folderId, currentFolderName)
        }
    }

    //delete folder
    fun deleteFolder(folderId: Int) {
        //add to recently deleted first before deleting
        viewModelScope.launch {
            folderRepository.deleteFolder(folderId)
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                NoteFolderViewModel(
                    folderRepository = NoteFolderRepositoryImpl(LocalStorageDataSourceProvider.instance)
                )
            }
        }
    }
}