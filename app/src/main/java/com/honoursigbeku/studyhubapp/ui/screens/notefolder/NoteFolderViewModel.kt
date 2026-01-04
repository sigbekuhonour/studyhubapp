package com.honoursigbeku.studyhubapp.ui.screens.notefolder

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.honoursigbeku.studyhubapp.R
import com.honoursigbeku.studyhubapp.domain.repository.AuthRepository
import com.honoursigbeku.studyhubapp.domain.repository.NoteFolderRepository
import com.honoursigbeku.studyhubapp.ui.screens.authentication.AuthState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class NoteFolderViewModel(
    private val authRepository: AuthRepository,
    private val folderRepository: NoteFolderRepository,
) : ViewModel() {


    val folders: StateFlow<List<Folder>> = authRepository.authState()
        .onStart {
            val currentId = authRepository.getCurrentUserId()
            if (currentId != null) emit(AuthState.Authenticated(currentId))
        }
        .flatMapLatest { state ->
            when (state) {
                is AuthState.Authenticated -> {
                    Log.i(
                        "NoteFolderViewModel",
                        "User detected: ${state.userId}. Fetching folders..."
                    )
                    folderRepository.getAllFolders(state.userId)
                }

                else -> {
                    Log.i("NoteFolderViewModel", "User not authenticated yet.")
                    flowOf(emptyList())
                }
            }
        }
        .map { folderList ->
            folderList.map { eachFolder ->
                Folder(
                    id = eachFolder.id,
                    icon = getIcon(eachFolder),
                    title = eachFolder.title
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )


    fun getFolderContentSize(folderId: String): StateFlow<Int> {
        return folderRepository.getFolderContentSize(folderId)
            .stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = 0)
    }

    private fun getIcon(folder: com.honoursigbeku.studyhubapp.domain.model.Folder): Int {
        var icon: Int = R.drawable.folder_icon
        if (folder.title == "Shared Notes") {
            icon = R.drawable.shared_folder
        } else if (folder.title == "Deleted Notes") {
            icon = R.drawable.deleted_folder
        }
        return icon
    }

    fun addFolder(name: String) {
        viewModelScope.launch {
            authRepository.getCurrentUserId()?.let {
                folderRepository.addFolder(name, it)
            }
        }
    }

    fun updateFolderName(folderId: String, currentFolderName: String) {
        viewModelScope.launch {
            authRepository.getCurrentUserId()?.let {
                folderRepository.updateFolderName(
                    folderId = folderId,
                    newFolderName = currentFolderName,
                    userId = it
                )
            }
        }
    }

    fun deleteFolder(folderId: String) {
        viewModelScope.launch {
            authRepository.getCurrentUserId()?.let {
                folderRepository.deleteFolder(folderId = folderId, userId = it)
            }
        }
    }

    companion object {
        fun Factory(
            authRepository: AuthRepository,
            folderRepository: NoteFolderRepository,
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                NoteFolderViewModel(
                    authRepository = authRepository,
                    folderRepository = folderRepository
                )
            }
        }
    }
}