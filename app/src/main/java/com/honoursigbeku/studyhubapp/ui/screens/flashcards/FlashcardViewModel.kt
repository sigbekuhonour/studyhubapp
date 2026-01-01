package com.honoursigbeku.studyhubapp.ui.screens.flashcards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.honoursigbeku.studyhubapp.data.datasource.local.LocalDataSource
import com.honoursigbeku.studyhubapp.data.datasource.remote.RemoteDataSource
import com.honoursigbeku.studyhubapp.data.repository.FlashcardRepositoryImpl
import com.honoursigbeku.studyhubapp.domain.repository.FlashcardRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FlashcardViewModel(
    private val flashcardRepository: FlashcardRepository
) : ViewModel() {
    private var _flashcards = flashcardRepository.getAllFlashcards().distinctUntilChanged()

    val flashcards: StateFlow<List<Flashcard>> = _flashcards.map { flashcards ->
        flashcards.map { eachFlashcard ->
            Flashcard(
                id = eachFlashcard.id,
                noteId = eachFlashcard.ownerNoteId,
                content = eachFlashcard.content
            )
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, emptyList())

    fun addFlashcard(content: String, noteId: String) {
        viewModelScope.launch {
            flashcardRepository.addFlashcard(ownerNoteId = noteId, content = content)
        }
    }

    fun deleteFlashcard(flashcardId: String, noteId: String) {
        viewModelScope.launch {
            flashcardRepository.deleteFlashcardByNoteId(flashcardId = flashcardId, noteId = noteId)
        }
    }

    companion object {
        fun Factory(
            localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                FlashcardViewModel(
                    flashcardRepository = FlashcardRepositoryImpl(
                        localDataSourceImpl = localDataSource,
                        remoteDataSourceImpl = remoteDataSource
                    )
                )
            }
        }
    }
}